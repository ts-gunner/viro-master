"""
faker 用户数据的脚本

前置条件：
1. 安装依赖包： pip install faker sqlalchemy pymysql
2. 修改参数
3. 运行脚本
"""

import pymysql
from faker import Faker
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, Session
from sqlalchemy import text
import time
import threading

pymysql.install_as_MySQLdb()


class SnowflakeIdGenerator:
    # 时间戳开始时间： 2021-01-01 08:00:00
    START_TIMESTAMP = 1609459200000

    # 每部分占用的位数
    TIMESTAMP_BIT = 41  # 时间戳占用的部分
    WORKER_BIT = 10  # 机器码
    SEQUENCE_BIT = 12  # 序列码

    # 每部分的最大值
    MAX_SEQUENCE = ~(-1 << SEQUENCE_BIT)
    MAX_WORKER_ID = ~(-1 << WORKER_BIT)

    # 每部分向左的位移
    WORKER_LEFT = SEQUENCE_BIT
    TIMESTAMP_LEFT = SEQUENCE_BIT + WORKER_BIT

    def __init__(self, work_id):
        if work_id > self.MAX_WORKER_ID or work_id < 0:
            raise ValueError(f"Work Id can't be greater than {self.MAX_WORKER_ID} or less than 0")
        self.work_id = work_id
        self.sequence = 0
        self.last_timestamp = -1
        self.lock = threading.Lock()

    def next_id(self):
        with self.lock:
            timestamp = int(time.time() * 1000)  # 当前时间戳（毫秒）
            if timestamp < self.last_timestamp:
                raise RuntimeError("Clock moved backwards. Refusing to generate id")

            if self.last_timestamp == timestamp:
                self.sequence = (self.sequence + 1) & self.MAX_SEQUENCE
                if self.sequence == 0:
                    timestamp = self.til_next_millis(self.last_timestamp)
            else:
                self.sequence = 0

            self.last_timestamp = timestamp

            return ((timestamp - self.START_TIMESTAMP) << self.TIMESTAMP_LEFT) | (self.work_id << self.WORKER_LEFT) | self.sequence

    def til_next_millis(self, start_timestamp):
        timestamp = int(time.time() * 1000)
        while timestamp <= start_timestamp:
            timestamp = int(time.time() * 1000)
        return timestamp

# ----------------------------------------------参数------------------------------------------------------------------------------
worker_id = 1
MYSQL_USER_NAME = "root"
MYSQL_PASSWORD = "123456"
MYSQL_HOST = "127.0.0.1"
MYSQL_PORT = "3306"
MYSQL_DB = "viro_center"
total_count = 500 * 10000

# ---------------------------------------------------------运行脚本-------------------------------------------------------------------
connection_str = f"mysql+pymysql://{MYSQL_USER_NAME}:{MYSQL_PASSWORD}@{MYSQL_HOST}:{MYSQL_PORT}/{MYSQL_DB}?charset=utf8mb4"
_metastore = create_engine(connection_str, pool_recycle=1500, pool_timeout=3600)
_session = sessionmaker(bind=_metastore)
session: Session = _session()
sf = SnowflakeIdGenerator(worker_id)

fake = Faker("zh_CN")
for i in range(total_count):
    if i > 0 and i % 5000 == 0:
        print(f"写入5000行， 还剩{total_count - i}待写入...")
        session.commit()
    dic = {
        "user_id": sf.next_id(),
        "user_name": fake.name(),
        "card_id": fake.ssn(),
        "email": fake.email(),
        "phone_number": fake.phone_number(),
        "address": fake.address(),
        "birth": fake.date_of_birth().strftime("%Y-%d-%m"),
        "sex": fake.random_element(elements=('男', '女')),
        "career": fake.job(),
        "nation": '中国'
    }
    session.execute(
        text(
            f"INSERT INTO `user_info`(`user_id`, `user_name`, `card_id`, `email`, `phone_number`, `address`, `birth`, `sex`, `career`, `nation`) VALUES(:user_id, :user_name, :card_id, :email, :phone_number, :address, :birth, :sex, :career, :nation)"),
        dic)

session.commit()

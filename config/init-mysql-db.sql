
-- create database
create database if not exists viro_center default charset=utf8mb4;
use viro_center;


-- 银行用户表
DROP TABLE IF EXISTS `dsc_user_info`;
create table if not exists `dsc_user_info`
(
    user_id bigint primary key comment 'user id',
    user_name varchar(255) not null comment '用户名称',
    `card_id` varchar(30) not null comment '身份证号码',
    email varchar(255) null comment '邮箱',
    phone_number varchar(11) null comment '手机号码',
    address varchar(255) null comment '地址',
    birth varchar(10) null comment '生日，格式：2024-01-01',
    sex char(6) null comment '性别',
    career varchar(100) null comment '职业',
    nation varchar(100) null comment '国籍',
    is_delete tinyint default 0 not null comment '是否删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) default charset=utf8mb4 comment '银行用户表';

-- 银行卡管理
DROP TABLE IF EXISTS `dsc_bank_card`;
create table if not exists `dsc_bank_card`
(
    credit_id bigint primary key comment '卡id',
    credit_account VARCHAR(50) not null comment '银行卡账号',
    credit_belong VARCHAR(50) not null comment '银行卡所属人',
    bank_name VARCHAR(50) not null comment '开户行',
    `open_time` varchar(40) not null comment '开户时间',
    `remain_amount` DECIMAL(12, 2) NOT NULL comment '银行卡余额',
    `credit_type` tinyint NOT NULL comment '银行卡种类，0: 借记卡，1：信用卡',
    `credit_status` tinyint NOT NULL comment '银行卡状态，0: 有效， 1：挂失， 2：过期',
    `transaction_limit` DECIMAL(10, 2) NOT NULL comment '交易限制',
    `user_id` bigint NOT NULL comment '用户id',
    remark VARCHAR(255) NULL comment '备注',
    is_delete tinyint default 0 not null comment '是否删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) default charset=utf8mb4 comment '银行卡管理表';



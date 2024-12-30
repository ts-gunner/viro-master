package com.forty.viro.core.utils;

/**
 * 雪花算法，生成分布式Id, 总共64位
 */
public class SnowflakeIdGenerator {
    // 时间戳开始时间： 2021-01-01 08:00:00
    private final static long START_TIMESTAMP = 1609459200000L;

    // 每部分占用的位数
    private final static long TIMESTAMP_BIT = 41;  // 时间戳占用的部分
    private final static long WORKER_BIT = 10;  // 机器码
    private final static long SEQUENCE_BIT = 12;  // 序列码

    // 每部分的最大值
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_BIT);

    // 每部分向左的位移
    private final static long WORKER_LEFT = SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + WORKER_BIT;

    /**
     * 工作机器ID
     */
    private long workId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次生成的时间戳
     */
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workId) {
        if (workId > MAX_WORKER_ID || workId < 0) {
            throw new IllegalArgumentException("Work Id can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.workId = workId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT) | (workId << WORKER_LEFT) | sequence;
    }

    private long tilNextMillis(long startTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= startTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    public static void main(String[] args) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);
        long id = snowflakeIdGenerator.nextId();
        System.out.println("Generated ID: " + id);
    }
}

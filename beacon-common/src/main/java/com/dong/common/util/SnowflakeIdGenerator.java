package com.dong.common.util;


import java.time.Instant;

/**
 * 雪花算法ID生成器
 * 结构：0 | 时间戳差值（41位） | 数据中心ID（5位） | 机器ID（5位） | 序列号（12位）
 */
public class SnowflakeIdGenerator {
    // 各部分的位数
    private static final long SEQUENCE_BITS = 12;   // 序列号位数
    private static final long WORKER_ID_BITS = 5;   // 机器ID位数
    private static final long DATA_CENTER_ID_BITS = 5; // 数据中心ID位数

    // 最大值计算（位运算优化）
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    // 偏移量
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    // 初始时间戳（2020-01-01 00:00:00）
    private static final long INIT_EPOCH = 1577836800000L;

    private final long workerId;      // 机器ID
    private final long dataCenterId;  // 数据中心ID
    private long sequence = 0L;       // 序列号
    private long lastTimestamp = -1L; // 上次生成时间戳

    /**
     * 构造函数
     * @param dataCenterId 数据中心ID (0-31)
     * @param workerId     机器ID (0-31)
     */
    public SnowflakeIdGenerator(long dataCenterId, long workerId) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("数据中心ID范围错误: 0-" + MAX_DATA_CENTER_ID);
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("机器ID范围错误: 0-" + MAX_WORKER_ID);
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    /**
     * 生成下一个ID（线程安全）
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 时钟回拨处理
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，拒绝生成ID。回拨时间：" + (lastTimestamp - timestamp) + "ms");
        }

        // 同一毫秒内生成
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) { // 当前毫秒序列号用尽，等待下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 组合各部分数值
        return ((timestamp - INIT_EPOCH) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 等待下一毫秒
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     */
    private long timeGen() {
        return Instant.now().toEpochMilli();
    }

    // ---------- 工具方法 ----------
    private static volatile SnowflakeIdGenerator instance;

    /**
     * 获取单例（根据需求决定是否需要单例）
     */
    public static SnowflakeIdGenerator getInstance(long dataCenterId, long workerId) {
        if (instance == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (instance == null) {
                    instance = new SnowflakeIdGenerator(dataCenterId, workerId);
                }
            }
        }
        return instance;
    }

    /**
     * 解析ID
     * @param id
     */
    public static void parseId(long id) {
        long timestamp = (id >> TIMESTAMP_SHIFT) + INIT_EPOCH;
        long dataCenterId = (id >> DATA_CENTER_ID_SHIFT) & MAX_DATA_CENTER_ID;
        long workerId = (id >> WORKER_ID_SHIFT) & MAX_WORKER_ID;
        long sequence = id & MAX_SEQUENCE;

        System.out.printf("ID成分：时间=%tF %tT, 数据中心=%d, 机器=%d, 序列号=%d\n",
                timestamp, timestamp, dataCenterId, workerId, sequence);
    }

    // 测试用例
    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println("生成的ID: " + generator.nextId());
        }
    }
}

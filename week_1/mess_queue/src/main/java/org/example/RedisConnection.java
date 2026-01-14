package org.example;

import redis.clients.jedis.Jedis;

public class RedisConnection {
    public static Jedis getConnection() {
        return new Jedis("localhost", 6379);
    }
}

package org.example.bai1;

import redis.clients.jedis.Jedis;

public class Producer {
    public static void main(String[] args) {
        Jedis jedis = RedisConnection.getConnection();

        for (int i = 1; i <= 10; i++) {
            String msg = "Order #" + i;
            //lpush: List Push
            jedis.lpush("order-queue", msg);
            System.out.println("Sent: " + msg);
        }
    }
}

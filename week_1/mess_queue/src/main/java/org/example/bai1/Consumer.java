package org.example.bai1;

import redis.clients.jedis.Jedis;
import java.util.List;

public class Consumer {
    public static void main(String[] args) {
        Jedis jedis = RedisConnection.getConnection();

        System.out.println("Waiting for messages...");

        while (true) {
            //brpop: Blocking Right Pop
            List<String> message = jedis.brpop(0, "order-queue");
            System.out.println("Processing: " + message.get(1));
        }
    }
}

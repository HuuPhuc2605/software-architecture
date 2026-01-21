package org.example.bai3;

import redis.clients.jedis.Jedis;

public class OrderService {

    public void placeOrder(String orderId, String email) {
        // 1. Giả lập lưu DB
        System.out.println("Tạo đơn hàng: " + orderId);

        // 2. Đẩy job gửi email vào queue
        try (Jedis jedis = new Jedis()) {
            String job = orderId + "|" + email;
            //lpush: Left Push
            jedis.lpush("email_queue", job);
            System.out.println("Đẩy job gửi email vào queue");
        }
    }
}

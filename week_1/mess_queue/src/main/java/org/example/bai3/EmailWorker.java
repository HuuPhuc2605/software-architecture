package org.example.bai3;

import redis.clients.jedis.Jedis;

import java.util.List;


public class EmailWorker {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis()) {
            System.out.println("Email Worker đang chạy...");

            while (true) {
                // Chờ job (blocking)
                //brpop: Blocking Right POP
                List<String> result = jedis.brpop(0, "email_queue");

                String job = result.get(1);
                String[] data = job.split("\\|");

                String orderId = data[0];
                String email = data[1];

                sendEmail(orderId, email);
            }
        }
    }

    private static void sendEmail(String orderId, String email) {
        System.out.println("Đang gửi email cho đơn " + orderId + " tới " + email);

        try {
            Thread.sleep(3000); // giả lập gửi email
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Gửi email thành công cho đơn " + orderId);
    }
}

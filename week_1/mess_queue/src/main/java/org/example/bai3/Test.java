package org.example.bai3;

public class Test {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        orderService.placeOrder("ORDER002", "lehuuphuc6573@gmail.com");
        System.out.println("Trả kết quả cho user ngay");
    }
}

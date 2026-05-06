package com.flashsale.order.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private String orderId;
    private String userId;
    private List<CartItem> items;
    private LocalDateTime timestamp;
    private String status;
    private String message;

    public Order() {
    }

    public Order(String orderId, String userId, List<CartItem> items, LocalDateTime timestamp, String status,
            String message) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.flashsale.order.service;

import com.flashsale.order.model.Order;
import com.flashsale.order.model.CartItem;
import com.flashsale.order.model.StockRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final StringRedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String inventoryBaseUrl;

    public OrderService(StringRedisTemplate redisTemplate,
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${inventory.base-url:http://localhost:8084}") String inventoryBaseUrl) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.inventoryBaseUrl = inventoryBaseUrl;
    }

    public Order checkout(String userId) {
        List<CartItem> cartItems = getCart(userId);
        if (cartItems.isEmpty()) {
            return new Order(null, userId, List.of(), LocalDateTime.now(), "FAILED", "Cart is empty");
        }

        for (CartItem item : cartItems) {
            Boolean success = restTemplate.postForObject(
                    inventoryBaseUrl + "/stock/decrease",
                    new StockRequest(item.getProductId(), item.getQuantity()),
                    Boolean.class);

            if (!Boolean.TRUE.equals(success)) {
                return new Order(null, userId, cartItems, LocalDateTime.now(), "FAILED",
                        "Out of stock for product " + item.getProductId());
            }
        }

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, cartItems, LocalDateTime.now(), "SUCCESS",
                "Order placed successfully");
        redisTemplate.opsForValue().set(orderKey(orderId), write(order));
        redisTemplate.delete(cartKey(userId));
        return order;
    }

    public Order getOrder(String orderId) {
        String payload = redisTemplate.opsForValue().get(orderKey(orderId));
        if (payload == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        return read(payload);
    }

    private List<CartItem> getCart(String userId) {
        List<String> payloads = redisTemplate.opsForList().range(cartKey(userId), 0, -1);
        if (payloads == null || payloads.isEmpty()) {
            return List.of();
        }

        List<CartItem> items = new ArrayList<>();
        for (String payload : payloads) {
            items.add(readCartItem(payload));
        }
        return items;
    }

    private String cartKey(String userId) {
        return "cart:" + userId;
    }

    private String orderKey(String orderId) {
        return "order:" + orderId;
    }

    private String write(Order order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to serialize order", exception);
        }
    }

    private Order read(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<Order>() {
            });
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to deserialize order", exception);
        }
    }

    private CartItem readCartItem(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<CartItem>() {
            });
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to deserialize cart item", exception);
        }
    }
}
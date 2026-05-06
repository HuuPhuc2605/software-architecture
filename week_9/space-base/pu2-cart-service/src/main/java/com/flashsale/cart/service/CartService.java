package com.flashsale.cart.service;

import com.flashsale.cart.model.CartItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public CartService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void addToCart(String userId, CartItem item) {
        String key = key(userId);
        redisTemplate.opsForList().rightPush(key, write(item));
        redisTemplate.expire(key, Duration.ofHours(2));
    }

    public List<CartItem> getCart(String userId) {
        List<String> payloads = redisTemplate.opsForList().range(key(userId), 0, -1);
        if (payloads == null || payloads.isEmpty()) {
            return List.of();
        }
        return payloads.stream().map(this::read).toList();
    }

    public void clearCart(String userId) {
        redisTemplate.delete(key(userId));
    }

    private String key(String userId) {
        return "cart:" + userId;
    }

    private String write(CartItem item) {
        try {
            return objectMapper.writeValueAsString(item);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to serialize cart item", exception);
        }
    }

    private CartItem read(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<CartItem>() {
            });
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to deserialize cart item", exception);
        }
    }
}
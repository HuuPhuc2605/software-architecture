package com.flashsale.inventory.service;

import com.flashsale.inventory.model.StockItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public InventoryService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public StockItem getStock(Long productId) {
        String stockValue = redisTemplate.opsForValue().get(stockKey(productId));
        if (stockValue == null) {
            return new StockItem(productId, "Unknown", 0, null);
        }

        String metadata = redisTemplate.opsForValue().get(metaKey(productId));
        StockItem stockItem = metadata == null ? new StockItem(productId, "Unknown", 0, null) : read(metadata);
        stockItem.setStock(Integer.parseInt(stockValue));
        return stockItem;
    }

    public boolean decreaseStock(Long productId, int quantity) {
        String key = stockKey(productId);
        Long remaining = redisTemplate.opsForValue().decrement(key, quantity);
        if (remaining == null) {
            return false;
        }
        if (remaining < 0) {
            redisTemplate.opsForValue().increment(key, quantity);
            return false;
        }
        return true;
    }

    public boolean increaseStock(Long productId, int quantity) {
        Long value = redisTemplate.opsForValue().increment(stockKey(productId), quantity);
        return value != null;
    }

    private String stockKey(Long productId) {
        return "stock:" + productId;
    }

    private String metaKey(Long productId) {
        return "stock-meta:" + productId;
    }

    private StockItem read(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<StockItem>() {
            });
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to deserialize stock item", exception);
        }
    }
}
package com.flashsale.product.service;

import com.flashsale.product.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public ProductService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Product> getAllProducts() {
        var keys = redisTemplate.keys("product:*");
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }

        List<Product> products = new ArrayList<>();
        for (String key : keys) {
            String payload = redisTemplate.opsForValue().get(key);
            if (payload != null) {
                products.add(readProduct(payload));
            }
        }

        products.sort(Comparator.comparing(Product::getId));
        return products;
    }

    public Product getProductById(Long id) {
        String payload = redisTemplate.opsForValue().get("product:" + id);
        if (payload == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        return readProduct(payload);
    }

    private Product readProduct(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<Product>() {
            });
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to deserialize product", exception);
        }
    }
}
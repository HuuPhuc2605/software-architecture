package com.flashsale.product;

import com.flashsale.product.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedProducts(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        return args -> {
            List<Product> products = List.of(
                    new Product(1L, "iPhone 15 Pro", new BigDecimal("25000000"), "Apple iPhone 15 Pro 256GB", 100),
                    new Product(2L, "Samsung S24", new BigDecimal("20000000"), "Samsung Galaxy S24", 50),
                    new Product(3L, "MacBook Air M3", new BigDecimal("35000000"), "Apple MacBook Air M3", 30));

            for (Product product : products) {
                String key = "product:" + product.getId();
                if (redisTemplate.opsForValue().get(key) == null) {
                    redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(product));
                }
            }
        };
    }
}
package com.flashsale.inventory;

import com.flashsale.inventory.model.StockItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedStocks(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        return args -> {
            List<StockItem> stocks = List.of(
                    new StockItem(1L, "iPhone 15 Pro", 100, new BigDecimal("25000000")),
                    new StockItem(2L, "Samsung S24", 50, new BigDecimal("20000000")),
                    new StockItem(3L, "MacBook Air M3", 30, new BigDecimal("35000000")));

            for (StockItem stockItem : stocks) {
                String key = "stock:" + stockItem.getProductId();
                if (redisTemplate.opsForValue().get(key) == null) {
                    redisTemplate.opsForValue().set(key, String.valueOf(stockItem.getStock()));
                    redisTemplate.opsForValue().set("stock-meta:" + stockItem.getProductId(),
                            objectMapper.writeValueAsString(stockItem));
                }
            }
        };
    }
}
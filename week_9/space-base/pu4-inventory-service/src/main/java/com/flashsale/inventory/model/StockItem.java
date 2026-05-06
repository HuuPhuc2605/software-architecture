package com.flashsale.inventory.model;

import java.math.BigDecimal;

public class StockItem {

    private Long productId;
    private String productName;
    private int stock;
    private BigDecimal price;

    public StockItem() {
    }

    public StockItem(Long productId, String productName, int stock, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
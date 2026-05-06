package com.flashsale.cart.model;

import java.math.BigDecimal;

public class CartItem {

    private String userId;
    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String userId, Long productId, String productName, BigDecimal unitPrice, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
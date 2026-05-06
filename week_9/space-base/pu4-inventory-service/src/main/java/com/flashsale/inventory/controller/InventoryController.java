package com.flashsale.inventory.controller;

import com.flashsale.inventory.model.StockItem;
import com.flashsale.inventory.model.StockRequest;
import com.flashsale.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/stock")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public StockItem getStock(@PathVariable Long productId) {
        return inventoryService.getStock(productId);
    }

    @PostMapping("/decrease")
    public boolean decreaseStock(@RequestBody StockRequest request) {
        return inventoryService.decreaseStock(request.productId(), request.quantity());
    }

    @PostMapping("/increase")
    public boolean increaseStock(@RequestBody StockRequest request) {
        return inventoryService.increaseStock(request.productId(), request.quantity());
    }
}
package com.sparta.demo.product.controller.request;

import com.sparta.demo.product.service.command.ProductSaveCommand;
import java.math.BigDecimal;

public record ProductSaveRequest(
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {

    public ProductSaveCommand toCommand() {
        return new ProductSaveCommand(name, description, price, stock, categoryId);
    }
}

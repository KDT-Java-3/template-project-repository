package com.sparta.demo.product.controller.request;

import com.sparta.demo.product.service.command.ProductUpdateCommand;
import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {

    public ProductUpdateCommand toCommand(Long productId) {
        return new ProductUpdateCommand(productId, name, description, price, stock, categoryId);
    }
}

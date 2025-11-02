package com.sparta.commerce.domain.product.dto;

import com.sparta.commerce.entity.Product;

import java.math.BigDecimal;

public record SaveProductDto(
        Long id,
        String name,
        String description,
        Long categoryId,
        BigDecimal price,
        Integer stock
) {
    public static SaveProductDto of(Product product) {
        return new SaveProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getPrice(),
                product.getStock()
        );
    }
}

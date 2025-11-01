package com.spartaecommerce.product.presentation.controller.dto.response;

import com.spartaecommerce.product.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
    Long productId,
    String name,
    BigDecimal price,
    Integer stock,
    Long categoryId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getProductId(),
            product.getName(),
            product.getPrice().amount(),
            product.getStock(),
            product.getCategoryId(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}

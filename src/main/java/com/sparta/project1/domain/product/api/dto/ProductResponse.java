package com.sparta.project1.domain.product.api.dto;

import java.time.LocalDateTime;

public record ProductResponse(
        Long productId,
        String name,
        Long price,
        String description,
        Integer stock,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
        ) {
}

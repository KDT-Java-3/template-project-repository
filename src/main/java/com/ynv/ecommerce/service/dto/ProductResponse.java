package com.ynv.ecommerce.service.dto;

import com.ynv.ecommerce.entity.Product;
import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String categoryName
) {

    public static ProductResponse fromEntity(Product entity) {

        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCategory().getName()
        );

    }

}
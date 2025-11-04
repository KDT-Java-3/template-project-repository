package com.sparta.demo.product.service.command;

import java.math.BigDecimal;

public record ProductUpdateCommand(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {
}

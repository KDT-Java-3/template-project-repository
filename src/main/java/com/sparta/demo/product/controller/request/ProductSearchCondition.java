package com.sparta.demo.product.controller.request;

import java.math.BigDecimal;

public record ProductSearchCondition(
        Long categoryId,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String keyword
) {
}

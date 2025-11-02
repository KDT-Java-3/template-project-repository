package com.sparta.project1.domain.order.api.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public record OrderedProductResponse(
        Long productId,
        String name,
        Integer quantity,
        BigDecimal productPrice,
        BigDecimal price
) {
}

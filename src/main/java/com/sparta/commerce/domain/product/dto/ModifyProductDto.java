package com.sparta.commerce.domain.product.dto;

import java.math.BigDecimal;

public record ModifyProductDto (
        String name,
        String description,
        BigDecimal price
) {

}

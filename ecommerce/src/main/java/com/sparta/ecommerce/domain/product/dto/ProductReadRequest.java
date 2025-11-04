package com.sparta.ecommerce.domain.product.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductReadRequest {
    private Long id;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String name;
}

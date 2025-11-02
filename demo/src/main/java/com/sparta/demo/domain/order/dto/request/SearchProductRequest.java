package com.sparta.demo.domain.order.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SearchProductRequest {
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String keyword;
}


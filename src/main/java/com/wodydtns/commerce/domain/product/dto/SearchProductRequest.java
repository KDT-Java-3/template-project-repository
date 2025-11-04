package com.wodydtns.commerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchProductRequest {

    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private Long categoryId;
}

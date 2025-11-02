package com.example.stproject.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchCond {
    private Long categoryId;
    private Long maxPrice;
    private Long minPrice;
    private String keyword;
}

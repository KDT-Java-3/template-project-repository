package com.wodydtns.commerce.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchCategoryDto {
    private String name;
    private String description;
    private String product_name;
    private int product_price;
    private int product_stock;
}

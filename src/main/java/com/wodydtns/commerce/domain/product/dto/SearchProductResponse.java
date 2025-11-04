package com.wodydtns.commerce.domain.product.dto;

import com.wodydtns.commerce.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchProductResponse {

    private String name;
    private int price;
    private int stock;
    private String description;
    private Category category;
}

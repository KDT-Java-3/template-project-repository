package com.wodydtns.commerce.domain.category.dto;

import com.wodydtns.commerce.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchCategoryResponse {

    private String name;
    private String description;
    private List<Product> products;
}

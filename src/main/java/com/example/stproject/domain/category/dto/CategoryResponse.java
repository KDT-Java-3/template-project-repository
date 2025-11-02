package com.example.stproject.domain.category.dto;

import com.example.stproject.domain.product.dto.ProductResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private int productCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductResponse> products;
}

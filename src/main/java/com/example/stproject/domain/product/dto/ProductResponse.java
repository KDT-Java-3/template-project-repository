package com.example.stproject.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
    private Long categoryId;
}

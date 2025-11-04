package com.wodydtns.commerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

    private String name;
    private int price;
    private int stock;
    private String description;
    private Long categoryId;
}

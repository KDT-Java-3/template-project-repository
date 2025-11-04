package com.wodydtns.commerce.domain.product.dto;

import lombok.Getter;

@Getter
public class UpdateProductRequest {
    private String name;
    private int price;
    private int stock;
    private Long categoryId;
    private String description;
}

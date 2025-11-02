package com.wodydtns.commerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProductDto {

    private String name;
    private int price;
    private int stock;
    private Long categoryId;

}

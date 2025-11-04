package com.wodydtns.commerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProductDto {

    private String name;
    private Integer price;
    private Integer stock;
    private Long categoryId;
    private String description;

}

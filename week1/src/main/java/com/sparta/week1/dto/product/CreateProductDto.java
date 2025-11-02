package com.sparta.week1.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateProductDto {
    private String name;
    private Integer price;
    private Integer stock;
    private Long categoryId;
}
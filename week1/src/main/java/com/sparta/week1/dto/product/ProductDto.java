package com.sparta.week1.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto extends CreateProductDto {
    private Long id;
}
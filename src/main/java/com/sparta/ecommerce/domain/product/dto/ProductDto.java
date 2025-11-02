package com.sparta.ecommerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;

    public static ProductDto fromRequest(ProductRequestDto productRequestDto) {
        return ProductDto.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .stock(productRequestDto.getStock())
                .categoryId(productRequestDto.getCategoryId())
                .build();
    }

}

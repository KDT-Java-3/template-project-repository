package com.sparta.ecommerce.domain.product.dto;

import com.sparta.ecommerce.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .build();
    }
}

package com.wodydtns.commerce.domain.product.dto;

import java.time.LocalDateTime;

import com.wodydtns.commerce.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    Long id;
    Long categoryId;
    String name;
    String description;
    int price;
    int stock;

    LocalDateTime createdAt;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .build();
    }
}

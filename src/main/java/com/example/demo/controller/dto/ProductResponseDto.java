package com.example.demo.controller.dto;


import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponseDto {
    Long id;
    Long categoryId;
    String name;
//    @Builder.Default
    String description = "우리가게 첫 오픈기념 물품들";
    BigDecimal price;
    Integer stock;

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
    // productMapper.fromEntity(product);

}

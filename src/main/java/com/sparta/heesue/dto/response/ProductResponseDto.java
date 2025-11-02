package com.sparta.heesue.dto.response;

import com.sparta.heesue.entity.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private Long categoryId;
    private String categoryName;  // 카테고리 이름도 포함
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
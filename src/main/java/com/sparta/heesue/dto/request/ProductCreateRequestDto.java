package com.sparta.heesue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private Long categoryId;  // 카테고리 ID
}
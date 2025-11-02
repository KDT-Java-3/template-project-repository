package com.sparta.heesue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductUpdateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;  // null 가능 (변경 안 할 수도)
    private Long categoryId;
}
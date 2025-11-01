package com.sparta.restful_1week.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

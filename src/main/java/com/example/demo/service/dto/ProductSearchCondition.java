package com.example.demo.service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchCondition {
    String name;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Long categoryId;
    Integer minStock;
    Integer maxStock;
}

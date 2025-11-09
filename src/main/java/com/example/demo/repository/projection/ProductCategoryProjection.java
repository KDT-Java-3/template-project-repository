package com.example.demo.repository.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductCategoryProjection {
    private Long id;
    private String name;
    private BigDecimal price;

    private String categoryName;
}

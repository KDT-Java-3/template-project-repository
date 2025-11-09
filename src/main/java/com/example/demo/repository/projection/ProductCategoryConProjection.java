package com.example.demo.repository.projection;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductCategoryConProjection {
    private Long id;
    private String name;
    private BigDecimal price;
    private String categoryName;

    public ProductCategoryConProjection(
            Long id,
            String name,
            BigDecimal price,
            String categoryName
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }
}

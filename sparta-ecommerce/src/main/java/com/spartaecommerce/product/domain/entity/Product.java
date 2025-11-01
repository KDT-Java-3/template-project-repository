package com.spartaecommerce.product.domain.entity;

import com.spartaecommerce.common.domain.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Product {

    private Long productId;

    private String name;

    private Money price;

    private Integer stock;

    private Long categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Product createNew(
        String name,
        Money price,
        Integer stock,
        Long categoryId
    ) {
        return new Product(
            null,
            name,
            price,
            stock,
            categoryId,
            null,
            null
        );
    }

    public void update(
        String name,
        Money price,
        Integer stock,
        Long categoryId
    ) {
        // validation 추가
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }
}

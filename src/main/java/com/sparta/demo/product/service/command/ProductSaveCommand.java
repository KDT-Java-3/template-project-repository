package com.sparta.demo.product.service.command;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.product.domain.Product;
import java.math.BigDecimal;

public record ProductSaveCommand(
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {

    public Product toEntity(Category category) {
        return Product.create(description, category, name, price, stock);
    }
}

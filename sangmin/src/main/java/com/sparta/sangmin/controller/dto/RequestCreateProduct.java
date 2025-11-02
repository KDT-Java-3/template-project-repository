package com.sparta.sangmin.controller.dto;

import com.sparta.sangmin.domain.Category;
import com.sparta.sangmin.domain.Product;

public record RequestCreateProduct(
        String name,
        String description,
        Integer price,
        Integer stock,
        Long categoryId
) {
    public Product toEntity(Category category) {
        return new Product(name, description, price, stock, category);
    }
}

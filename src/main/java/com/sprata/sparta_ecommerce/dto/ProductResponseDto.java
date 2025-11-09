package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Long price;
    private final int stock;
    private final boolean stockActive;
    private final Long category_id;
    private final String category_name;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.stockActive = product.getStock() <= 0;
        this.category_id = Optional.ofNullable(product.getCategory())
                .map(Category::getId)
                .orElse(null);
        this.category_name = Optional.ofNullable(product.getCategory())
                .map(Category::getName)
                .orElse("");
    }
}

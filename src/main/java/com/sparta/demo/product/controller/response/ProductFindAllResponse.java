package com.sparta.demo.product.controller.response;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.product.domain.Product;
import java.math.BigDecimal;

public record ProductFindAllResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock,
        CategoryDto category
) {

    public record CategoryDto(
            Long id,
            String name
    ) {

        public static CategoryDto of(Category category) {
            return new CategoryDto(category.getId(), category.getName());
        }
    }

    public static ProductFindAllResponse of(Product product) {
        return new ProductFindAllResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? CategoryDto.of(product.getCategory()) : null
        );
    }
}

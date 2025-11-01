package com.sparta.demo.category.controller.response;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.product.domain.Product;
import java.math.BigDecimal;
import java.util.List;

public record CategoryFindAllResponse(
        Long id,
        String name,
        String description,
        List<ProductDto> products
) {

    public static CategoryFindAllResponse of(Category category, List<Product> products) {
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::of)
                .toList();

        return new CategoryFindAllResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productDtos
        );
    }

    public record ProductDto(
            Long id,
            String name,
            String description,
            BigDecimal price,
            int stock
    ) {

        public static ProductDto of(Product product) {
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
            );
        }
    }
}

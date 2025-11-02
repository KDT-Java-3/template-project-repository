package com.sparta.proejct1101.domain.product.dto.response;

import com.sparta.proejct1101.domain.category.dto.response.CategoryRes;
import com.sparta.proejct1101.domain.product.entity.Product;

public record ProductRes (
        String prodName,
        Integer price,
        Integer stock,
        String description,
        CategoryRes category
)
{
    static public ProductRes from(Product product) {
        return new ProductRes(
                product.getProdName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getCategory() != null ? CategoryRes.from(product.getCategory()) : null
        );
    }
}

package com.sparta.templateprojectrepository.dto.response;

import com.sparta.templateprojectrepository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;

    private String productName;

    private BigDecimal price;

    private int stock;

    private String description;

    private String categoryName;

    @Builder
    public static ProductResponseDto from (Product product) {

        return new ProductResponseDto(product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getCategory().getName()
        );

    }
}

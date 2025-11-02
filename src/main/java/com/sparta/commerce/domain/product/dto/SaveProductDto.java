package com.sparta.commerce.domain.product.dto;

import com.sparta.commerce.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record SaveProductDto(
        Long id,

        @NotBlank(message = "상품명은 필수입니다")
        String name,

        String description,

        @NotNull(message = "카테고리 ID는 필수입니다")
        Long categoryId,

        @NotNull(message = "가격은 필수입니다")
        @Positive(message = "가격은 0보다 커야 합니다")
        BigDecimal price,

        @NotNull(message = "재고는 필수입니다")
        @PositiveOrZero(message = "재고는 0 이상이어야 합니다")
        Integer stock
) {
    public static SaveProductDto of(Product product) {
        return new SaveProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getPrice(),
                product.getStock()
        );
    }
}

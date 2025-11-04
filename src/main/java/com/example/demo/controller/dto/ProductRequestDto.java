package com.example.demo.controller.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequestDto {
    Long categoryId;

    @NotBlank(message = "상품명은 필수입니다.")
    String name;

    String description;

    @NotNull(message = "가격은 필수입니다")
    @Positive(message = "가격이 양수여야 합니다")
    BigDecimal price;

    @NotNull(message = "재고는 필수입니다")
    @PositiveOrZero(message = "재고는 0 이상이어야 합니다")
    Integer stock;

    public Product toEntity(Category category) {
        return Product.builder()
                .category(category)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .build();
    }
}

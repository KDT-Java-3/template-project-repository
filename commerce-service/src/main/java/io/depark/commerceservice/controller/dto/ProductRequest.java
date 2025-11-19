package io.depark.commerceservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequest {

    private Long categoryId;

    @NotBlank
    private String name;

    private String description;

    @NotNull(message = "가격은 필수입니다")
    @Positive(message = "가격은 양수여야 합니다")
    private BigDecimal price;

    @NotNull(message = "재고는 필수입니다")
    @PositiveOrZero(message = "재고는 0 이상이어야 합니다")
    private Integer stock;
}

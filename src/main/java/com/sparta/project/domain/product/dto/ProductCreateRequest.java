package com.sparta.project.domain.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductCreateRequest {
    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;
    // --수정 필수값
    private Long categoryId; // 카테고리 ID
}

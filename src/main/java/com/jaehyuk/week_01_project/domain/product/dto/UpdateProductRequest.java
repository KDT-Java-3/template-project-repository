package com.jaehyuk.week_01_project.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * 상품 수정 요청 DTO
 *
 * @param name 상품 이름 (필수)
 * @param description 상품 설명 (선택)
 * @param price 상품 가격 (필수)
 * @param stock 재고 수량 (필수, 0 이상)
 * @param categoryId 카테고리 ID (필수)
 */
public record UpdateProductRequest(
        @NotBlank(message = "상품 이름은 필수입니다")
        String name,

        String description,

        @NotNull(message = "상품 가격은 필수입니다")
        BigDecimal price,

        @NotNull(message = "재고 수량은 필수입니다")
        @Min(value = 0, message = "재고는 0 이상이어야 합니다")
        Integer stock,

        @NotNull(message = "카테고리 ID는 필수입니다")
        Long categoryId
) {
}

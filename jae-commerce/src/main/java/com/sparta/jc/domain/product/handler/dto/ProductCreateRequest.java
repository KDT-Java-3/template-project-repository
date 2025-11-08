package com.sparta.jc.domain.product.handler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * 상품 생성 요청 시 사용하는 DTO
 * <p>
 * 필수 입력 필드: name, price, stock, category_id
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    /**
     * 상품명
     */
    @NotBlank
    private String name;
    /**
     * 가격
     */
    @NotBlank
    private BigDecimal price;
    /**
     * 재고 수량
     */
    @NotBlank
    private Integer stock;
    /**
     * 상품 설명
     */
    private String description;
    /**
     * 카테고리 ID (연관관계)
     */
    @NotBlank
    private Long categoryId;

}
package com.sparta.demo2.domain.product.handler.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * 상품 생성/수정 요청 시 사용하는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    /**
     * 상품명
     */
    private String name;
    /**
     * 상품 설명
     */
    private String description;
    /**
     * 가격
     */
    private BigDecimal price;
    /**
     * 재고 수량
     */
    private Integer stock;
    /**
     * 카테고리 ID (연관관계)
     */
    private Long categoryId;

}
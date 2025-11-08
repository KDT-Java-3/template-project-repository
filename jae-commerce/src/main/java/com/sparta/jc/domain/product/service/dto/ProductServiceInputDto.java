package com.sparta.jc.domain.product.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 상품 도메인 서비스 레이어 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceInputDto {

    /**
     * 카테고리 객체
     * 다대일 관계: 하나의 카테고리에 여러 상품이 속할 수 있음
     */
    private Long categoryId;
    /**
     * 상품명
     */
    private String name;
    /**
     * 상품 설명
     */
    private String description;
    /**
     * 상품 가격
     */
    private BigDecimal price;
    /**
     * 재고 수량
     */
    private Integer stock;
    /**
     * 생성일
     */
    private LocalDateTime createdAt;
    /**
     * 수정일
     */
    private LocalDateTime updatedAt;

}

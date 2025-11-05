package com.sparta.project.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductSearchCondition {
    private Long categoryId;        // 카테고리 ID로 필터링
    private String keyword;          // 상품명 키워드 검색
    private BigDecimal minPrice;     // 최소 가격
    private BigDecimal maxPrice;     // 최대 가격
}

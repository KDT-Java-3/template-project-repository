package com.example.demo.domain.product.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 검색 조건
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCondition {

    /**
     * 카테고리 ID로 필터링
     */
    private Long categoryId;

    /**
     * 최소 가격
     */
    private BigDecimal priceMin;

    /**
     * 최대 가격
     */
    private BigDecimal priceMax;

    /**
     * 재고 없는 상품 포함 여부 (null이면 전체, false면 재고 있는 것만)
     */
    private Boolean includeZeroStock;

    /**
     * 상품명 키워드 검색
     */
    private String nameKeyword;
}

package com.example.demo.domain.category.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리별 최다 판매 상품 Top 10 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTopProductDto {

    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long totalSales;  // 총 판매량
}

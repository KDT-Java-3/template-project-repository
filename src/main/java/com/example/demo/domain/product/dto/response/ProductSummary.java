package com.example.demo.domain.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 목록 조회용 DTO (QueryDSL Projections)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummary {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private LocalDateTime createdAt;
}

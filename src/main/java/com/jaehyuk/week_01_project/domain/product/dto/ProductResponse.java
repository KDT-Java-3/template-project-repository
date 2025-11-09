package com.jaehyuk.week_01_project.domain.product.dto;

import com.jaehyuk.week_01_project.domain.product.entity.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 상품 조회 응답 DTO
 */
@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        CategoryInfo category,
        LocalDateTime createdAt
) {
    /**
     * 카테고리 간단 정보 (중첩 레코드)
     */
    @Builder
    public record CategoryInfo(
            Long categoryId,
            String categoryName
    ) {
    }

    /**
     * Product 엔티티를 ProductResponse로 변환
     */
    public static ProductResponse from(Product product) {
        CategoryInfo categoryInfo = null;
        if (product.getCategory() != null) {
            categoryInfo = CategoryInfo.builder()
                    .categoryId(product.getCategory().getId())
                    .categoryName(product.getCategory().getName())
                    .build();
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(categoryInfo)
                .createdAt(product.getCreatedAt())
                .build();
    }
}

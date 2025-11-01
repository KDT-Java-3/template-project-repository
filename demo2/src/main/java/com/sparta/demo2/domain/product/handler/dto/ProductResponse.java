package com.sparta.demo2.domain.product.handler.dto;

import com.sparta.demo2.domain.product.entity.Product;
import lombok.*;

import java.math.BigDecimal;

/**
 * 상품 응답 DTO
 * Entity → DTO 변환용 정적 팩토리 메서드 포함
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String categoryName;

    // Entity → DTO 변환
    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryName(product.getCategory().getName())
                .build();
    }
}

package com.sparta.demo.controller.dto.product;

import com.sparta.demo.service.dto.product.CategoryInfo;
import com.sparta.demo.service.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller에서 클라이언트에게 반환하는 상품 응답 DTO
 * Service의 ProductDto를 변환하여 반환
 */
@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private List<CategoryInfo> categoryPath;  // 카테고리 계층 구조 (최상위부터 현재 카테고리까지)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * ProductDto를 ProductResponse로 변환하는 정적 팩토리 메서드
     */
    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getCategoryId(),
                dto.getCategoryName(),
                dto.getCategoryPath(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

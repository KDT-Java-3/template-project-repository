package com.sparta.demo.service.dto.product;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Layer에서 사용하는 상품 조회 DTO
 * Entity를 변환하여 Controller로 전달
 */
@Getter
@AllArgsConstructor
public class ProductDto {
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
     * Product Entity를 ProductDto로 변환하는 정적 팩토리 메서드
     */
    public static ProductDto from(Product product) {
        // 카테고리 계층 구조 생성 (역순으로 변환하여 최상위부터 정렬)
        List<Category> path = product.getCategory().getCategoryPath();
        List<CategoryInfo> categoryPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            categoryPath.add(CategoryInfo.from(path.get(i)));
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                categoryPath,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}

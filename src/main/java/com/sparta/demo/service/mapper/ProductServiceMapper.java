package com.sparta.demo.service.mapper;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.service.dto.product.CategoryInfo;
import com.sparta.demo.service.dto.product.ProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Product Service Layer Mapper
 * Entity → DTO 변환 담당
 */
@Component
public class ProductServiceMapper {

    /**
     * Product Entity를 ProductDto로 변환
     */
    public ProductDto toDto(Product product) {
        // 카테고리 계층 구조 생성 (역순으로 변환하여 최상위부터 정렬)
        List<Category> path = product.getCategory().getCategoryPath();
        List<CategoryInfo> categoryPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            Category category = path.get(i);
            categoryPath.add(new CategoryInfo(
                    category.getId(),
                    category.getName()
            ));
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

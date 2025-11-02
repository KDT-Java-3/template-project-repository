package com.sparta.demo.dto.product;

import com.sparta.demo.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 상품 응답에 포함되는 카테고리 정보
 */
@Getter
@AllArgsConstructor
public class CategoryInfo {
    private Long id;
    private String name;

    public static CategoryInfo from(Category category) {
        return new CategoryInfo(
                category.getId(),
                category.getName()
        );
    }
}

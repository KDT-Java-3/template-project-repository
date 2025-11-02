package com.sparta.demo.domain.order.dto.response;

import com.sparta.demo.domain.order.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    /** 카테고리 이름 */
    private String name;
    /** 카테고리 설명 */
    private String description;

    public static CategoryResponse buildFromEntity(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}

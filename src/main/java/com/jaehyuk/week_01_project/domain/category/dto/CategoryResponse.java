package com.jaehyuk.week_01_project.domain.category.dto;

import com.jaehyuk.week_01_project.domain.category.entity.Category;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 조회 응답 DTO (계층 구조)
 */
@Builder
public record CategoryResponse(
        Long id,
        String name,
        String description,
        Long parentId,
        List<CategoryResponse> children
) {
    /**
     * Category 엔티티를 CategoryResponse로 변환 (자식 카테고리는 빈 리스트로 초기화)
     */
    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .children(new ArrayList<>())
                .build();
    }

    /**
     * 자식 카테고리를 추가합니다 (계층 구조 생성용)
     */
    public CategoryResponse withChildren(List<CategoryResponse> children) {
        return CategoryResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .parentId(this.parentId)
                .children(children)
                .build();
    }
}

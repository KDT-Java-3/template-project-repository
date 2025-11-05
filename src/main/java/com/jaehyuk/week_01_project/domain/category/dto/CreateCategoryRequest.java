package com.jaehyuk.week_01_project.domain.category.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 카테고리 생성 요청 DTO
 *
 * @param name 카테고리 이름 (필수)
 * @param description 카테고리설명 (선택)
 * @param parentId 부모 카테고리 ID (선택, null이면 최상위 카테고리)
 */
public record CreateCategoryRequest(
        @NotBlank(message = "카테고리 이름은 필수입니다")
        String name,
        String description,
        Long parentId
) {}

package com.jaehyuk.week_01_project.domain.category.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 카테고리 변경 요청 DTO
 *
 * @param name 카테고리 이름 (필수)
 * @param description 카테고리 설명 (선택)
 */
public record UpdateCategoryRequests(
        @NotBlank(message = "카테고리 이름은 필수입니다")
        String name,
        String description
) {}

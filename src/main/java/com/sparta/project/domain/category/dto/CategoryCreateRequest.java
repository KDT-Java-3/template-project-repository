package com.sparta.project.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryCreateRequest {
    @NotBlank(message = "카테고리 이름은 필수입니다.")
    private String name;
    private Long parentId; // 부모 카테고리 ID (null이면 최상위 카테고리)
}

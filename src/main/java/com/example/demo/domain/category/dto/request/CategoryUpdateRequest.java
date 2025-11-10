package com.example.demo.domain.category.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {

    @Size(max = 100, message = "카테고리 이름은 최대 100자까지 입력 가능합니다.")
    private String name;

    private String description;

    private Long parentId;
}

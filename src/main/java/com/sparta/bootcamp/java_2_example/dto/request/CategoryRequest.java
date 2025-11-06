package com.sparta.bootcamp.java_2_example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "카테고리명은 필수입니다")
    @Size(max = 50, message = "카테고리명은 최대 50자까지 입력 가능합니다")
    private String name;

    private String description;

    // 상위 카테고리 ID (null이면 최상위 카테고리)
    private Long parentId;
}

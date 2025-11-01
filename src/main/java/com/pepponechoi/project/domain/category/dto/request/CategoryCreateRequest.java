package com.pepponechoi.project.domain.category.dto.request;

import com.pepponechoi.project.domain.category.entity.Category;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequest {
    @NotBlank(message = "카테고리 이름은 필수입니다.")
    private String name;
    private String description;
}

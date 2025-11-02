package com.sparta.demo.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    @Size(max = 100, message = "카테고리 이름은 100자 이하여야 합니다.")
    private String name;

    private String description;

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

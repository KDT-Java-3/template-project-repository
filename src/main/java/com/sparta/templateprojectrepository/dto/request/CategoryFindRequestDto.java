package com.sparta.templateprojectrepository.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryFindRequestDto {
    @NotBlank(message = "카테고리를 선택하세요")
    private Long categoryId;
}

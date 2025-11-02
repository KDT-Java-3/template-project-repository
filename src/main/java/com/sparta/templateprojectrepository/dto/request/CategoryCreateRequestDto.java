package com.sparta.templateprojectrepository.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequestDto {

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 1, max = 250)
    private String name;

    private Long parentId;

    private String description;
}

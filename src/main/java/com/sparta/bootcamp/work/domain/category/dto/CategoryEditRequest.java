package com.sparta.bootcamp.work.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryEditRequest {

    @NotBlank(message = "ID는 필수입니다.")
    private Long id;

    @Size(min = 2, max = 50)
    private String name;

    private String description;
}

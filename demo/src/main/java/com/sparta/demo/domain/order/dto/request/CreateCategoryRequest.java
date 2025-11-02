package com.sparta.demo.domain.order.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCategoryRequest {
    @NotNull(message = "카테고리명은 필수입니다.")
    private String name;

    @Nullable
    @Size(max = 255)
    private String description;
}

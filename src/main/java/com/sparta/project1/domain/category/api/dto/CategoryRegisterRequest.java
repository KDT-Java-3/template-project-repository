package com.sparta.project1.domain.category.api.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryRegisterRequest(
        @NotNull
        String name,
        String description,
        Long parentId
) {

}

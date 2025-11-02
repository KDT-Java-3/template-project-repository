package com.sparta.proejct1101.domain.category.dto.request;

import jakarta.validation.constraints.NotNull;

public record CategoryReq(
        Long parentId,

        Long id,

        @NotNull
        String name,

        String description)
{ }

package com.example.stproject.domain.category.dto;

import lombok.Getter;
import lombok.Setter;
import org.wildfly.common.annotation.NotNull;

@Getter
@Setter
public class CategoryUpdateRequest {
    @NotNull
    private Long id;

    private String name;

    private String description;
}

package io.depark.commerceservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequest {

    @NotBlank
    private String name;

    private String description;

    private Long parentId;
}

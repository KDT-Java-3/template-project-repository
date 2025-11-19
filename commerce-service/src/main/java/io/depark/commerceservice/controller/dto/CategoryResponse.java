package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@Builder
public class CategoryResponse {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Long parentId;

    public static CategoryResponse fromEntity(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentId(!isEmpty(category.getParent()) ? category.getParent().getId() : null)
                .build();
    }
}

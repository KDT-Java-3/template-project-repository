package com.sparta.templateprojectrepository.dto.response;

import com.sparta.templateprojectrepository.entity.Category;
import com.sparta.templateprojectrepository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;

    private LocalDateTime updatedAt;

    private String name;

    private String description;

    private Long parentId;

    @Builder
    public static CategoryResponseDto from (Category category) {

        return new CategoryResponseDto(
                category.getId(),
                category.getUpdatedAt(),
                category.getName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getId() : null
        );

    }
}

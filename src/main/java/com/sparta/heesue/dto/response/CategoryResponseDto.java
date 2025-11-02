package com.sparta.heesue.dto.response;

import com.sparta.heesue.entity.Category;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResponseDto> children;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
        this.children = category.getChildren().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}

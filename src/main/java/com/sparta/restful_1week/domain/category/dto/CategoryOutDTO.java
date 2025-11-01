package com.sparta.restful_1week.domain.category.dto;

import com.sparta.restful_1week.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutDTO {

    private Long id;
    private String name;
    private String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public CategoryOutDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
}

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
public class CategoryCretOutDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Category fromEntity(Category category) {
        return Category.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .build();
    }

}

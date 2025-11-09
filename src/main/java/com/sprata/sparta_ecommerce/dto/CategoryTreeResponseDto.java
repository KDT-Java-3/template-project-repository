package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Setter
public class CategoryTreeResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<CategoryTreeResponseDto> children = new ArrayList<>();

    public CategoryTreeResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            this.children = category.getSubCategories().stream()
                    .map(CategoryTreeResponseDto::new)
                    .collect(Collectors.toList());
        }
    }
}

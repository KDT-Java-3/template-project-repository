package com.example.demo.controller.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponseDto {

    private Long categoryId;
    private String name;
    private Category parent;
    private List<Category> children = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static CategoryResponseDto fromEntity(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .children(category.getChildren())
                .products(category.getProducts())
                .build();
    }
}

package com.example.demo.controller.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequestDto {

    private Long id;
    private String name;
    private Category parent;
    private final List<Category> children = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}

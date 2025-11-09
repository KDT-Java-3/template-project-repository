package com.example.demo.service.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryServiceInputDto {
    private Long categoryId;
    private String name;
    private Category parent;
    private List<Category> children = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

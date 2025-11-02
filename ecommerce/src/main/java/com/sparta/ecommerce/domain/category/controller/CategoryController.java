package com.sparta.ecommerce.domain.category.controller;

import com.sparta.ecommerce.domain.category.dto.CategoryCreateRequest;
import com.sparta.ecommerce.domain.category.dto.CategoryResponse;
import com.sparta.ecommerce.domain.category.dto.CategoryUpdateRequest;
import com.sparta.ecommerce.domain.category.service.CategoryService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/categories"})
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreateRequest dto) {
        CategoryResponse response = this.categoryService.createCategory(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responses = this.categoryService.getAllCategories();
        return ResponseEntity.ok(responses);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequest dto) {
        dto.setId(id);
        CategoryResponse response = this.categoryService.updateCategory(dto);
        return ResponseEntity.ok(response);
    }

    @Generated
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
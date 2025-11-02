package com.wodydtns.commerce.domain.category.controller;

import com.wodydtns.commerce.domain.category.dto.CreateCategoryRequest;
import com.wodydtns.commerce.domain.category.dto.SearchCategoryResponse;
import com.wodydtns.commerce.domain.category.dto.UpdateCategoryRequest;
import com.wodydtns.commerce.domain.category.entity.Category;
import com.wodydtns.commerce.domain.category.service.CategoryService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest dto) {
        Category category = categoryService.createCategory(dto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest dto) {
        Category category = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<SearchCategoryResponse>> searchAllWithProducts() {
        List<SearchCategoryResponse> categories = categoryService.findAllWithProducts();
        return ResponseEntity.ok(categories);
    }
}

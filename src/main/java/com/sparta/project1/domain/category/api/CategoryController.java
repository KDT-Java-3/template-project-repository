package com.sparta.project1.domain.category.api;

import com.sparta.project1.domain.category.api.dto.CategoryRegisterRequest;
import com.sparta.project1.domain.category.api.dto.CategoryResponse;
import com.sparta.project1.domain.category.api.dto.CategoryUpdateRequest;
import com.sparta.project1.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody CategoryRegisterRequest request) {
        categoryService.register(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {
        List<CategoryResponse> response = categoryService.getAllCategories();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryUpdateRequest request) {
        categoryService.updateCategory(id, request);

        return ResponseEntity.ok().build();
    }
}

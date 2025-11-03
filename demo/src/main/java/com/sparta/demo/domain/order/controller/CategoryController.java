package com.sparta.demo.domain.order.controller;

import com.sparta.demo.domain.order.dto.request.CreateCategoryRequest;
import com.sparta.demo.domain.order.dto.request.UpdateCategoryRequest;
import com.sparta.demo.domain.order.dto.response.AllCategoryResponse;
import com.sparta.demo.domain.order.dto.response.CategoryResponse;
import com.sparta.demo.domain.order.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    // 등록
    @PostMapping
    public CategoryResponse registCategory(@Valid @RequestBody CreateCategoryRequest request) throws Exception {
        return categoryService.registCategory(request);
    }

    // 조회
    @GetMapping("/all")
    public AllCategoryResponse getAllCategory() {
        return categoryService.getAllCategories();
    }

    // 수정
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) throws Exception {
        request.setId(id);
        return categoryService.updateCategory(request);
    }
}

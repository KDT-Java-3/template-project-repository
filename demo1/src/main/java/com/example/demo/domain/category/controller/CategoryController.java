package com.example.demo.domain.category.controller;

import com.example.demo.domain.category.dto.CategoryResponseDto;
import com.example.demo.domain.category.dto.CreateCategoryRequestDto;
import com.example.demo.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto category){
        CategoryResponseDto res = categoryService.createCategory(category);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getAllCategoriesById(@PathVariable Long id){
        CategoryResponseDto category = categoryService.getCategorybyId(id);
        return ResponseEntity.ok(category);
    }

}

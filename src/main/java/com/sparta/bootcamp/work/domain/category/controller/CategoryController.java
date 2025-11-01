package com.sparta.bootcamp.work.domain.category.controller;

import com.sparta.bootcamp.work.domain.category.dto.CategoryCreateRequest;
import com.sparta.bootcamp.work.domain.category.dto.CategoryDto;
import com.sparta.bootcamp.work.domain.category.dto.CategoryEditRequest;
import com.sparta.bootcamp.work.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.work.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getCategory() {
        return ResponseEntity.ok(categoryService.getCategory());
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        Long id = categoryService.createCategory(categoryCreateRequest);
        return ResponseEntity.ok("Category created with id: " + id);
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryDto> entityCategory(@RequestBody CategoryEditRequest categoryEditRequest) {
        CategoryDto categoryDto = categoryService.editCategory(categoryEditRequest);
        return ResponseEntity.ok(categoryDto);
    }


}

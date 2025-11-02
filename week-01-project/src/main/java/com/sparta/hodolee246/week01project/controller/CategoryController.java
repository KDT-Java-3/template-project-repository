package com.sparta.hodolee246.week01project.controller;

import com.sparta.hodolee246.week01project.controller.request.CategoryRequest;
import com.sparta.hodolee246.week01project.service.CategoryService;
import com.sparta.hodolee246.week01project.service.request.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok().body(categoryService.addCategory(new CategoryDto(request.name(), request.description())));
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, new CategoryDto(request.name(), request.description())));
    }

}

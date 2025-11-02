package com.sparta.demo1.controller;

import com.sparta.demo1.entity.Category;
import com.sparta.demo1.service.CategoryService;
import com.sparta.demo1.service.dto.CategoryRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 전체 조회
    @GetMapping("/select-all")
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.selectCategory();
        System.out.println(categories);
        return categories;
    }

    // 카테고리 등록
    @PostMapping("/insert")
    public void createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
    }

    // 카테고리 수정
    @PostMapping("/update")
    public void updateCategory(@Valid @RequestBody CategoryRequest request) {
        categoryService.updateCategory(request);
    }

}

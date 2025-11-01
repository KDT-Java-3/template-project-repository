package com.sparta.restful_1week.domain.category.controller;

import com.sparta.restful_1week.domain.category.dto.CategoryInDTO;
import com.sparta.restful_1week.domain.category.dto.CategoryOutDTO;
import com.sparta.restful_1week.domain.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @PostMapping("/categories")
    public CategoryOutDTO createCategory(@RequestBody CategoryInDTO categoryInDTO) {
        CategoryService categoryService = new CategoryService();
        return categoryService.createCategory(categoryInDTO);
    }

    @GetMapping("/categories")
    public List<CategoryOutDTO> getCategories() {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategories();
    }

    @PutMapping("/categories/{id}")
    public CategoryOutDTO updateCategory(@PathVariable Long id, @RequestBody CategoryInDTO categoryInDTO) {
        CategoryService categoryService = new CategoryService();
        return categoryService.updateCategory(id, categoryInDTO);
    }

}

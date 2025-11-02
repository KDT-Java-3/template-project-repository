package com.example.week01_project.web;

import com.example.week01_project.common.ApiResponse;
import com.example.week01_project.domain.category.Category;
import com.example.week01_project.dto.category.CategoryDtos.*;
import com.example.week01_project.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<Resp> create(@RequestBody @Valid CreateReq req) {
        return ApiResponse.ok(categoryService.create(req));
    }

    @GetMapping
    public ApiResponse<List<Category>> list() {
        return ApiResponse.ok(categoryService.list());
    }

    @PutMapping("/{id}")
    public ApiResponse<Resp> update(@PathVariable Long id, @RequestBody @Valid UpdateReq req) {
        return ApiResponse.ok(categoryService.update(id, req));
    }
}


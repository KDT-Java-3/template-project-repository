package com.study.shop.domain.controller;

import com.study.shop.domain.dto.CategoryDto;
import com.study.shop.domain.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto.Resp create(@Valid @RequestBody CategoryDto.CreateCategory req) {
        return categoryService.createCategory(req);
    }

    @GetMapping
    public List<CategoryDto.Resp> findAll(
            @RequestParam(defaultValue = "false") boolean includeProducts) {
        return categoryService.findAll(includeProducts);
    }

    @PatchMapping("/{id}")
    public CategoryDto.Resp update(@PathVariable Long id, @RequestBody CategoryDto.UpdateCategory req) {
        return categoryService.update(id, req);
    }
}

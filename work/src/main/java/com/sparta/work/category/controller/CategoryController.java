package com.sparta.work.category.controller;

import com.sparta.work.category.dto.request.RequestCreateCategoryDto;
import com.sparta.work.category.dto.request.RequestUpdateCategoryDto;
import com.sparta.work.category.dto.response.ResponseCategoryDto;
import com.sparta.work.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> findAllCategory() {
        return ResponseEntity.ok(categoryService.findAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> findCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid RequestCreateCategoryDto dto) {

        Long id = categoryService.createCategory(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateCategory(@PathVariable Long id, @RequestBody RequestUpdateCategoryDto dto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }
}

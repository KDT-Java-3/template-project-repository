package com.sparta.commerce.management.controller;

import com.sparta.commerce.management.dto.request.category.CategoryCreateRequest;
import com.sparta.commerce.management.dto.request.category.CategoryUpdateRequest;
import com.sparta.commerce.management.dto.response.category.CategoryResponse;
import com.sparta.commerce.management.entity.Category;
import com.sparta.commerce.management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateRequest request){
        return ResponseEntity.ok(categoryService.update(id, request));
    }
}

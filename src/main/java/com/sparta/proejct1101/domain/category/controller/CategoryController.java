package com.sparta.proejct1101.domain.category.controller;

import com.sparta.proejct1101.domain.category.dto.request.CategoryReq;
import com.sparta.proejct1101.domain.category.dto.response.CategoryRes;
import com.sparta.proejct1101.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryRes> createCategory(@RequestBody CategoryReq req){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(req));
    }

    @GetMapping
    public ResponseEntity<List<CategoryRes>> getCategory(){
        return ResponseEntity.ok(categoryService.getCategory());
    }

    @PutMapping
    public ResponseEntity<CategoryRes> updateCategory(@RequestBody CategoryReq req){
        return ResponseEntity.ok(categoryService.updateCategory(req));
    }
}

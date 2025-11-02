package com.sparta.week01project.domain.category.controller;

import com.sparta.week01project.domain.category.controller.request.CategoryCreateRequest;
import com.sparta.week01project.domain.category.controller.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/category")
public class CategoryController {

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryCreateRequest request) {
        return null;
    }
}

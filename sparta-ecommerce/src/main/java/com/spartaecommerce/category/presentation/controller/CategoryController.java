package com.spartaecommerce.category.presentation.controller;

import com.spartaecommerce.category.application.CategoryService;
import com.spartaecommerce.category.domain.commnad.CategoryRegisterCommand;
import com.spartaecommerce.category.presentation.controller.dto.request.CategoryRegisterRequest;
import com.spartaecommerce.common.domain.CommonResponse;
import com.spartaecommerce.common.domain.IdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CommonResponse<IdResponse>> createProduct(
        @Valid @RequestBody CategoryRegisterRequest registerRequest
    ) {
        CategoryRegisterCommand registerCommand = registerRequest.toCommand();

        Long categoryId = categoryService.register(registerCommand);

        return ResponseEntity
            .created(URI.create("/api/v1/categories/" + categoryId))
            .body(CommonResponse.create(categoryId));
    }
}

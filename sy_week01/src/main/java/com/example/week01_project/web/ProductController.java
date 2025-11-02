package com.example.week01_project.web;

import com.example.week01_project.common.ApiResponse;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.dto.product.ProductDtos.*;
import com.example.week01_project.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ApiResponse<Resp> create(@RequestBody @Valid CreateReq req) {
        return ApiResponse.ok(productService.create(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<Resp> get(@PathVariable Long id) {
        return ApiResponse.ok(productService.get(id));
    }

    @GetMapping
    public ApiResponse<List<Product>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword
    ) {
        return ApiResponse.ok(productService.list(categoryId, minPrice, maxPrice, keyword));
    }

    @PutMapping("/{id}")
    public ApiResponse<Resp> update(@PathVariable Long id, @RequestBody @Valid UpdateReq req) {
        return ApiResponse.ok(productService.update(id, req));
    }
}

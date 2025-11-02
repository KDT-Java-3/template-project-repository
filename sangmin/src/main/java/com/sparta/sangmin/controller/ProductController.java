package com.sparta.sangmin.controller;

import com.sparta.sangmin.controller.dto.ProductResponse;
import com.sparta.sangmin.controller.dto.RequestCreateProduct;
import com.sparta.sangmin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody RequestCreateProduct request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String keyword
    ) {
        // 검색 조건이 하나라도 있으면 검색, 없으면 전체 조회
        if (categoryId != null || minPrice != null || maxPrice != null || keyword != null) {
            List<ProductResponse> products = productService.searchProducts(categoryId, minPrice, maxPrice, keyword);
            return ResponseEntity.ok(products);
        } else {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody RequestCreateProduct request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }
}


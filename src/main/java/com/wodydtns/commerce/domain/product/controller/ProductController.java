package com.wodydtns.commerce.domain.product.controller;

import com.wodydtns.commerce.domain.product.dto.CreateProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductRequest;
import com.wodydtns.commerce.domain.product.dto.SearchProductResponse;
import com.wodydtns.commerce.domain.product.dto.UpdateProductRequest;
import com.wodydtns.commerce.domain.product.entity.Product;
import com.wodydtns.commerce.domain.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
            @RequestBody UpdateProductRequest updateProductRequest) {
        Product product = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        Product product = productService.findProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchProductResponse>> searchProducts(
            @RequestBody SearchProductRequest SearchProductRequest) {
        List<SearchProductResponse> products = productService.searchProducts(SearchProductRequest);
        return ResponseEntity.ok(products);
    }
}

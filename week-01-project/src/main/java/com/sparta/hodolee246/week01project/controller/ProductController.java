package com.sparta.hodolee246.week01project.controller;

import com.sparta.hodolee246.week01project.controller.request.ProductRequest;
import com.sparta.hodolee246.week01project.service.ProductService;
import com.sparta.hodolee246.week01project.service.request.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("product")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.ok().body(productService.addProduct(new ProductDto(product.name(), product.price(), product.stock(), product.categoryId())));
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @PutMapping("{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequest product) {
        return ResponseEntity.ok().body(productService.updateProduct(productId, new ProductDto(product.name(), product.price(), product.stock(), product.categoryId())));
    }

}

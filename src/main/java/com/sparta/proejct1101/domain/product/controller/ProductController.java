package com.sparta.proejct1101.domain.product.controller;

import com.sparta.proejct1101.domain.product.dto.request.ProductReq;
import com.sparta.proejct1101.domain.product.dto.request.ProductSearchReq;
import com.sparta.proejct1101.domain.product.dto.response.ProductRes;
import com.sparta.proejct1101.domain.product.entity.Product;
import com.sparta.proejct1101.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductRes> createProduct(ProductReq req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductRes> updateProduct(@PathVariable Long id, @RequestBody ProductReq req) {
        return  ResponseEntity.ok(productService.updateProduct(id, req));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductRes> getProduct(@PathVariable Long id) {
        return  ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductRes>> getProducts() {
        return  ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductRes>> searchProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String keyword) {
        ProductSearchReq searchReq = new ProductSearchReq(categoryId, minPrice, maxPrice, keyword);
        return ResponseEntity.ok(productService.searchProducts(searchReq));
    }

}

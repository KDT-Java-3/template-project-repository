package com.sparta.ecommerce.domain.product.controller;

import com.sparta.ecommerce.domain.product.dto.ProductCreateRequest;
import com.sparta.ecommerce.domain.product.dto.ProductReadRequest;
import com.sparta.ecommerce.domain.product.dto.ProductResponse;
import com.sparta.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.sparta.ecommerce.domain.product.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/products"})
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping({"/{id}"})
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductReadRequest dto = new ProductReadRequest(id, null, null, null, null);
        dto.setId(id);
        ProductResponse response = productService.readProduct(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String name, @RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice) {
        ProductReadRequest dto = new ProductReadRequest(null, categoryId, minPrice, maxPrice, name);
        List<ProductResponse> responses = productService.raedProducts(dto);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest dto) {
        ProductResponse response = productService.createProduct(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest dto) {
        dto.setId(id);
        ProductResponse response = productService.updateProduct(dto);
        return ResponseEntity.ok(response);
    }
}

package com.sparta.work.product.controller;

import com.sparta.work.product.domain.Product;
import com.sparta.work.product.dto.request.RequestCreateProductDto;
import com.sparta.work.product.dto.request.RequestUpdateProductDto;
import com.sparta.work.product.dto.response.ResponseProductDto;
import com.sparta.work.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> findProduct(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String name) {
        if (categoryId != null) return ResponseEntity.ok(productService.findByCategoryId(categoryId));
        if (name != null) return ResponseEntity.ok(productService.findByNameContainingIgnoreCase(name));
        return ResponseEntity.ok(productService.findAllProduct());
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid RequestCreateProductDto dto) {
        Long id = productService.createProduct(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody @Valid RequestUpdateProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }
}

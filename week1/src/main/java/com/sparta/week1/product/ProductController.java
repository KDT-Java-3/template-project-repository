package com.sparta.week1.product;

import com.sparta.week1.dto.product.CreateProductDto;
import com.sparta.week1.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String keyword
    ) {
        List<Product> products = service.getAllProducts(categoryId, minPrice, maxPrice, keyword);
        return ResponseEntity.ok(products);
    }

    /**
     * 상품 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto dto) {
        Product product = service.createProduct(dto);
        return ResponseEntity.ok(product);
    }

    /**
     * 상품 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody CreateProductDto dto) {
        Product product = service.updateProduct(id, dto);
        return ResponseEntity.ok(product);
    }
}

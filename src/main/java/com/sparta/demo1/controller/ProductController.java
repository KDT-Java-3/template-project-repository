package com.sparta.demo1.controller;

import com.sparta.demo1.entity.Category;
import com.sparta.demo1.entity.Product;
import com.sparta.demo1.service.CategoryService;
import com.sparta.demo1.service.ProductService;
import com.sparta.demo1.service.dto.CategoryRequest;
import com.sparta.demo1.service.dto.ProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 카테고리 전체 조회
    @GetMapping("/select-all")
    public void getAllProducts() {
        List<Product> products = productService.selectProducts(null,null,null,null);
        System.out.println(products);
    }

    // 카테고리 등록
    @PostMapping("/insert")
    public void createProduct(@Valid @RequestBody ProductRequest request) {
        productService.createProduct(request);
    }

    // 카테고리 수정
    @PostMapping("/update")
    public void updateProduct(@Valid @RequestBody ProductRequest request) {

        productService.updateProduct(request);
    }

}

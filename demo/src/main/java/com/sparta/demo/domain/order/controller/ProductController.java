package com.sparta.demo.domain.order.controller;

import com.sparta.demo.domain.order.dto.request.CreateProductRequest;
import com.sparta.demo.domain.order.dto.request.SearchProductRequest;
import com.sparta.demo.domain.order.dto.request.UpdateProductRequest;
import com.sparta.demo.domain.order.dto.response.ProductResponse;
import com.sparta.demo.domain.order.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    // 등록
    @PostMapping
    public ProductResponse createProduct(@RequestBody @Valid CreateProductRequest request) throws Exception {
        return productService.createProduct(request);
    }

    //전체 조회
    @GetMapping("/all")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id){
        return productService.getProductById(id);
    }

    // 검색 기능
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@ModelAttribute SearchProductRequest request) {
        return productService.searchProducts(
                request.getCategoryId(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getKeyword()
        );
    }

    // 수정
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@RequestBody @Valid UpdateProductRequest request, @PathVariable Long id) throws Exception {
        request.setId(id);
        return productService.updateProduct(request);
    }
}

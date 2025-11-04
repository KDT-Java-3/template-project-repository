package com.sparta.demo.product.controller;

import com.sparta.demo.product.controller.request.ProductSaveRequest;
import com.sparta.demo.product.controller.request.ProductSearchCondition;
import com.sparta.demo.product.controller.request.ProductUpdateRequest;
import com.sparta.demo.product.controller.response.ProductFindAllResponse;
import com.sparta.demo.product.domain.Product;
import com.sparta.demo.product.service.ProductService;
import com.sparta.demo.product.service.command.ProductSaveCommand;
import com.sparta.demo.product.service.command.ProductUpdateCommand;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Void> save(@RequestBody ProductSaveRequest request) {
        ProductSaveCommand command = request.toCommand();
        Long id = productService.save(command);
        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductFindAllResponse>> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword
    ) {
        var condition = new ProductSearchCondition(categoryId, minPrice, maxPrice, keyword);
        List<Product> products = productService.findAll(condition);
        List<ProductFindAllResponse> responses = products.stream()
                .map(ProductFindAllResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductFindAllResponse> findById(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        ProductFindAllResponse response = ProductFindAllResponse.of(product);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> update(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request
    ) {
        ProductUpdateCommand command = request.toCommand(productId);
        productService.update(command);
        return ResponseEntity.ok().build();
    }
}

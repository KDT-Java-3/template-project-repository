package com.spartaecommerce.product.presentation.controller;

import com.spartaecommerce.common.domain.CommonResponse;
import com.spartaecommerce.common.domain.IdResponse;
import com.spartaecommerce.product.application.dto.ProductRegisterCommand;
import com.spartaecommerce.product.application.service.ProductService;
import com.spartaecommerce.product.domain.Product;
import com.spartaecommerce.product.presentation.controller.dto.ProductRegisterRequest;
import com.spartaecommerce.product.presentation.controller.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<CommonResponse<IdResponse>> createProduct(
        @Valid @RequestBody ProductRegisterRequest request
    ) {
        ProductRegisterCommand registerCommand = request.toCommand();

        Long productId = productService.register(registerCommand);

        return ResponseEntity
            .created(URI.create("/api/v1/products/" + productId))
            .body(CommonResponse.create(productId));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProduct(
        @PathVariable Long productId
    ) {
        Product product = productService.getProduct(productId);
        ProductResponse productResponse = ProductResponse.from(product);

        CommonResponse<ProductResponse> commonResponse = CommonResponse.success(productResponse);
        return ResponseEntity.ok(commonResponse);
    }
}

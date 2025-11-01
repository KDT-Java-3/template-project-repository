package com.spartaecommerce.product.presentation.controller;

import com.spartaecommerce.common.domain.CommonResponse;
import com.spartaecommerce.common.domain.IdResponse;
import com.spartaecommerce.common.domain.PageResponse;
import com.spartaecommerce.product.application.service.ProductService;
import com.spartaecommerce.product.domain.command.ProductRegisterCommand;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.query.ProductSearchQuery;
import com.spartaecommerce.product.presentation.controller.dto.request.ProductRegisterRequest;
import com.spartaecommerce.product.presentation.controller.dto.request.ProductSearchRequest;
import com.spartaecommerce.product.presentation.controller.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/products")
    public ResponseEntity<CommonResponse<PageResponse<ProductResponse>>> searchProducts(
        @Valid ProductSearchRequest searchRequest
    ) {
        ProductSearchQuery searchQuery = searchRequest.toQuery();

        List<Product> products = productService.searchProducts(searchQuery);
        List<ProductResponse> response = products.stream()
            .map(ProductResponse::from)
            .toList();

        return ResponseEntity.ok(CommonResponse.success(PageResponse.of(response)));
    }
}

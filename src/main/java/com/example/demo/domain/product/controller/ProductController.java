package com.example.demo.domain.product.controller;

import com.example.demo.domain.product.dto.request.ProductCreateRequest;
import com.example.demo.domain.product.dto.request.ProductSearchCondition;
import com.example.demo.domain.product.dto.request.ProductUpdateRequest;
import com.example.demo.domain.product.dto.response.ProductResponse;
import com.example.demo.domain.product.dto.response.ProductSummary;
import com.example.demo.domain.product.service.ProductService;
import com.example.demo.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping
    public ApiResponse<Long> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Long productId = productService.createProduct(request);
        return ApiResponse.success(productId);
    }

    @Operation(summary = "상품 단건 조회", description = "ID로 특정 상품을 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ApiResponse.success(response);
    }

    @Operation(
        summary = "상품 검색 (조건부 + 페이징 + 정렬)",
        description = "카테고리, 가격 범위, 재고 상태, 상품명 키워드로 검색하고 페이징 및 정렬을 적용합니다."
    )
    @GetMapping
    public ApiResponse<Page<ProductSummary>> searchProducts(
        @Parameter(description = "카테고리 ID") @RequestParam(required = false) Long categoryId,
        @Parameter(description = "최소 가격") @RequestParam(required = false) BigDecimal priceMin,
        @Parameter(description = "최대 가격") @RequestParam(required = false) BigDecimal priceMax,
        @Parameter(description = "재고 없는 상품 포함 여부") @RequestParam(required = false) Boolean includeZeroStock,
        @Parameter(description = "상품명 키워드") @RequestParam(required = false) String nameKeyword,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ProductSearchCondition condition = ProductSearchCondition.builder()
            .categoryId(categoryId)
            .priceMin(priceMin)
            .priceMax(priceMax)
            .includeZeroStock(includeZeroStock)
            .nameKeyword(nameKeyword)
            .build();

        Page<ProductSummary> result = productService.searchProducts(condition, pageable);
        return ApiResponse.success(result);
    }

    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductUpdateRequest request
    ) {
        ProductResponse response = productService.updateProduct(id, request);
        return ApiResponse.success(response);
    }

    @Operation(
        summary = "상품 삭제",
        description = "상품을 삭제합니다. 완료된 주문이 있는 경우 삭제할 수 없습니다."
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.success();
    }
}

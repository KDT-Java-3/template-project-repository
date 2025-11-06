package com.sparta.demo.controller;

import com.sparta.demo.common.ApiResponse;
import com.sparta.demo.controller.dto.product.ProductRequest;
import com.sparta.demo.controller.dto.product.ProductResponse;
import com.sparta.demo.controller.mapper.ProductControllerMapper;
import com.sparta.demo.service.ProductService;
import com.sparta.demo.service.dto.product.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Product", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductControllerMapper mapper;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        // Request → Service DTO 변환
        var createDto = mapper.toCreateDto(request);

        // Service 호출
        ProductDto productDto = productService.createProduct(createDto);

        // Service DTO → Response 변환
        ProductResponse response = mapper.toResponse(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @Operation(summary = "상품 단건 조회", description = "ID로 상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        ProductDto productDto = productService.getProduct(id);
        ProductResponse response = mapper.toResponse(productDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "상품 조회 및 검색",
               description = "상품을 조회합니다. Query Parameter로 필터링 가능: category-id, min-price, max-price, keyword")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @RequestParam(name = "category-id", required = false) Long categoryId,
            @RequestParam(name = "min-price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max-price", required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword) {
        List<ProductDto> productDtos = productService.searchProducts(categoryId, minPrice, maxPrice, keyword);
        List<ProductResponse> responses = productDtos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        // Request → Service DTO 변환
        var updateDto = mapper.toUpdateDto(request);

        // Service 호출
        ProductDto productDto = productService.updateProduct(id, updateDto);

        // Service DTO → Response 변환
        ProductResponse response = mapper.toResponse(productDto);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}

package com.jaehyuk.week_01_project.domain.product.controller;

import com.jaehyuk.week_01_project.config.auth.LoginUser;
import com.jaehyuk.week_01_project.domain.product.dto.CreateProductRequest;
import com.jaehyuk.week_01_project.domain.product.dto.ProductResponse;
import com.jaehyuk.week_01_project.domain.product.dto.UpdateProductRequest;
import com.jaehyuk.week_01_project.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product", description = "상품 관리 API")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "상품 등록",
            description = """
                    새로운 상품을 등록합니다. (로그인 필요)

                    - 필수 입력: name, price, stock, categoryId
                    - 선택 입력: description
                    - categoryId에 해당하는 카테고리가 존재해야 합니다
                    """
    )
    @PostMapping("/v1")
    public ResponseEntity<Long> createProduct(
            @LoginUser Long userId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        log.debug("상품 등록 요청 - userId: {}, name: {}, categoryId: {}, price: {}, stock: {}",
                userId, request.name(), request.categoryId(), request.price(), request.stock());

        Long productId = productService.createProduct(request);

        return ResponseEntity.ok(productId);
    }

    @Operation(
            summary = "단일 상품 조회",
            description = """
                    특정 상품의 상세 정보를 조회합니다. (로그인 필요)

                    - 상품 기본 정보와 카테고리 정보가 포함됩니다
                    """
    )
    @GetMapping("/v1/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @LoginUser Long userId,
            @PathVariable Long productId
    ) {
        log.debug("상품 조회 요청 - userId: {}, productId: {}", userId, productId);

        ProductResponse product = productService.getProduct(productId);

        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "상품 목록 조회",
            description = """
                    상품 목록을 조회합니다. (로그인 필요)

                    - 필터링 옵션 (선택적):
                      * categoryId: 카테고리 ID
                      * minPrice: 최소 가격
                      * maxPrice: 최대 가격
                      * keyword: 상품명 검색 키워드
                    - 페이징 옵션:
                      * page: 페이지 번호 (0부터 시작, 기본값: 0)
                      * size: 페이지 크기 (기본값: 20)
                      * sort: 정렬 (예: price,asc 또는 createdAt,desc)

                    예시:
                    - GET /api/product/v1?page=0&size=10
                    - GET /api/product/v1?categoryId=1&minPrice=10000&maxPrice=50000
                    - GET /api/product/v1?keyword=iPhone&page=0&size=20&sort=price,asc
                    """
    )
    @GetMapping("/v1")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @LoginUser Long userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        log.debug("상품 목록 조회 요청 - userId: {}, categoryId: {}, minPrice: {}, maxPrice: {}, keyword: {}, page: {}, size: {}",
                userId, categoryId, minPrice, maxPrice, keyword, pageable.getPageNumber(), pageable.getPageSize());

        Page<ProductResponse> products = productService.getProducts(categoryId, minPrice, maxPrice, keyword, pageable);

        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "상품 수정",
            description = """
                    기존 상품 정보를 수정합니다. (로그인 필요)

                    - 모든 필드를 필수로 입력해야 합니다 (전체 수정)
                    - 변경 가능한 필드: name, description, price, stock, categoryId
                    - categoryId에 해당하는 카테고리가 존재해야 합니다
                    """
    )
    @PutMapping("/v1/{productId}")
    public ResponseEntity<Long> updateProduct(
            @LoginUser Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        log.debug("상품 수정 요청 - userId: {}, productId: {}, name: {}, categoryId: {}, price: {}, stock: {}",
                userId, productId, request.name(), request.categoryId(), request.price(), request.stock());

        Long updatedProductId = productService.updateProduct(productId, request);

        return ResponseEntity.ok(updatedProductId);
    }
}

package com.example.stproject.domain.product.controller;

import com.example.stproject.domain.product.dto.ProductCreateRequest;
import com.example.stproject.domain.product.dto.ProductResponse;
import com.example.stproject.domain.product.dto.ProductSearchCond;
import com.example.stproject.domain.product.dto.ProductUpdateRequest;
import com.example.stproject.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    /*
    * - **상품 등록 API**
    - 상품 이름, 설명, 가격, 재고, 카테고리 정보를 포함하여 새로운 상품을 등록할 수 있는 API를 구현합니다.
    - 필수 입력 필드: `name`, `price`, `stock`, `category_id`.
    *
    * - **상품 조회 API**
    - 단일 상품 상세 조회 및 전체 상품 리스트를 조회하는 API를 구현합니다.
    - 검색 및 필터링 조건: 카테고리, 가격 범위, 상품명 키워드.
    *
    * - **상품 수정 API**
    - 기존에 등록된 상품 정보를 수정할 수 있는 API를 구현합니다.
    - 변경 가능한 필드: `name`, `description`, `price`, `stock`, `category_id`.
    * */

    @Operation(
            summary = "상품 등록 API",
            description = "새로운 상품을 등록합니다."
    )
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid ProductCreateRequest req) {
        Long id = productService.create(req);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "상품 단건 조회 API",
            description = "상품 단건 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getOne(id));
    }

    @Operation(
            summary = "상품 전체 조회 API",
            description = "상품 전체 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Operation(
            summary = "상품 수정 API",
            description = "기존에 등록된 상품 정보를 수정합니다"
    )
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody @Valid ProductUpdateRequest req) {
        return ResponseEntity.ok(productService.update(req));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> search(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        ProductSearchCond cond = new ProductSearchCond();
        cond.setCategoryId(categoryId);
        cond.setMinPrice(minPrice);
        cond.setMaxPrice(maxPrice);
        cond.setKeyword(keyword);
        return ResponseEntity.ok(productService.search(cond, pageable));
    }
}

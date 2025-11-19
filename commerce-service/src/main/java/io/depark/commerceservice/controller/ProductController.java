package io.depark.commerceservice.controller;

import io.depark.commerceservice.common.ApiResponse;
import io.depark.commerceservice.controller.dto.ProductRequest;
import io.depark.commerceservice.controller.dto.ProductResponse;
import io.depark.commerceservice.controller.dto.ProductSearchCondition;
import io.depark.commerceservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long id) {
        return ApiResponse.success(productService.getProduct(id));
    }

    @GetMapping
    public ApiResponse<Page<ProductResponse>> searchProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean includeZeroStock,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .categoryId(categoryId)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .includeZeroStock(includeZeroStock)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        return ApiResponse.success(productService.searchProducts(condition, pageable));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest productRequest){
        return ApiResponse.created(productService.create(productRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody ProductRequest productRequest){
        return ApiResponse.success(productService.update(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ApiResponse.success();
    }
}

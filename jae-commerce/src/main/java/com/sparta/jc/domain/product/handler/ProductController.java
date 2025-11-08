package com.sparta.jc.domain.product.handler;

import com.sparta.jc.domain.product.handler.dto.ProductCreateRequest;
import com.sparta.jc.domain.product.handler.dto.ProductResponse;
import com.sparta.jc.domain.product.handler.dto.ProductUpdateRequest;
import com.sparta.jc.domain.product.converter.ProductConverter;
import com.sparta.jc.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 상품 관리 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    //
    private final ProductConverter productConverter;

    /**
     * 상품_신규_생성(등록) API
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        return ResponseEntity.ok(productService.createProduct(productConverter.toServiceInputDto(request)));
    }

    /**
     * 상품_수정(저장) API
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, @RequestBody ProductUpdateRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productConverter.toServiceInputDto(request)));
    }

    /*--------------------------------------- 이하 조회 ---------------------------------------*/

    /**
     * 상품 1건 조회 API
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

}

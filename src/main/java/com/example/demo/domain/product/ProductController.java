package com.example.demo.domain.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자 자동 생성 (의존성 주입)
@Tag(name = "Product", description = "Product management APIs") // Swagger 문서화
public class ProductController {
    // ===== 의존성 주입 ======
    private final ProductService productService;

    // ===== 1. 상품 등록 =====
    /** POST /api/products - 새 상품 등록 */
    @PostMapping
    @Operation(summary = "Create a new proudct", description = "Creates a new product with the provided information") // Swagger 문서화: API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product  created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or product already exists")
    }) // Swagger 문서화: API Response 설명
    public ResponseEntity<ProductDto.Response> createProduct(
            // @Valid: DTO 검증
            // @RequestBody: JSON → 객체 변환
            @Valid @RequestBody ProductDto.Request request
    ) {
        // Service 메서드 수행 (→ Repository → JPA 작업) 후, 응답 반환
        ProductDto.Response response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

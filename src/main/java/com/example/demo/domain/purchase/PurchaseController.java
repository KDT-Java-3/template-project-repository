package com.example.demo.domain.purchase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/purchases")
@RequiredArgsConstructor // 의존성 주입
@Tag(name = "Purchase", description = "Purchase management APIs") // Swagger 문서화
public class PurchaseController {
    // ===== 의존성 주입 =====
    private final PurchaseService purchaseService;

    // ===== 1. 주문 생성 =====
    /** POST /api/purchases - 새 주문 생성 */
    @PostMapping
    @Operation(summary = "Create a new purchase", description = "Creates a new purchase with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or purchase already exists")
    })
    public ResponseEntity<PurchaseDto.Response> createPurchase(@RequestBody @Valid PurchaseDto.Request request) {
        PurchaseDto.Response response = purchaseService.createPurchase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201 Created 응답
    }
}

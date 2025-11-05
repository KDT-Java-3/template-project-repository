package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.controller.dto.PurchaseRequest;
import com.example.demo.controller.dto.PurchaseResponse;
import com.example.demo.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    // [실습] 주문 생성 요청을 받아 PurchaseService 트랜잭션을 실행한다.
    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponse>> placePurchase(
            @Valid @RequestBody PurchaseRequest request
    ) {
        PurchaseResponse response = purchaseService.placePurchase(request);
        return ApiResponse.created(response);
    }
}

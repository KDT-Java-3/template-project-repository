package com.sparta.project.domain.purchase.controller;

import com.sparta.project.domain.purchase.dto.PurchaseCreateRequest;
import com.sparta.project.domain.purchase.dto.PurchaseResponse;
import com.sparta.project.domain.purchase.dto.PurchaseStatusUpdateRequest;
import com.sparta.project.domain.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * 주문 생성
     * POST /api/purchases
     */
    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(
            @Valid @RequestBody PurchaseCreateRequest request) {
        PurchaseResponse response = purchaseService.createPurchase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 사용자의 모든 주문 조회
     * GET /api/purchases/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseResponse>> getUserPurchases(
            @PathVariable Long userId) {
        List<PurchaseResponse> purchases = purchaseService.getUserPurchases(userId);
        return ResponseEntity.ok(purchases);
    }

    // --주문 상태 변경이랑 취소 API 가 왜 따로 있는지 모르겠음
    /**
     * 주문 상태 변경
     * PATCH /api/purchases/{purchaseId}/status
     */
    @PatchMapping("/{purchaseId}/status")
    public ResponseEntity<PurchaseResponse> updatePurchaseStatus(
            @PathVariable Long purchaseId,
            @Valid @RequestBody PurchaseStatusUpdateRequest request) {
        PurchaseResponse response = purchaseService.updatePurchaseStatus(purchaseId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 주문 취소
     * POST /api/purchases/{purchaseId}/cancel
     */
    @PostMapping("/{purchaseId}/cancel")
    public ResponseEntity<PurchaseResponse> cancelPurchase(
            @PathVariable Long purchaseId) {
        PurchaseResponse response = purchaseService.cancelPurchase(purchaseId);
        return ResponseEntity.ok(response);
    }

}

package com.sparta.commerce.management.controller;

import com.sparta.commerce.management.dto.request.purchase.PurchaseCreateRequest;
import com.sparta.commerce.management.dto.request.purchase.PurchaseSearchRequest;
import com.sparta.commerce.management.dto.request.purchase.PurchaseUpdateRequest;
import com.sparta.commerce.management.dto.response.purchase.PurchaseResponse;
import com.sparta.commerce.management.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    //주문 생성 API
    @PostMapping("/save")
    public ResponseEntity<PurchaseResponse> save(@RequestBody @Valid PurchaseCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.save(request));
    }

    //주문 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<List<PurchaseResponse>> findAllByUserId(@PathVariable UUID userId){
        return ResponseEntity.ok(purchaseService.findAllByUserId(userId));
    }

    //주문 상태 변경 API
    @PutMapping("/{productId}/status")
    public ResponseEntity<PurchaseResponse> updateStatus(@PathVariable UUID productId, @RequestBody @Valid PurchaseUpdateRequest request){
        return ResponseEntity.ok(purchaseService.updateStatus(productId, request));
    }

    //주문 취소 API
    @PutMapping("/{productId}/cancel")
    public ResponseEntity<PurchaseResponse> cancelPurchase(@PathVariable UUID productId, @RequestBody @Valid PurchaseUpdateRequest request){
        return ResponseEntity.ok(purchaseService.cancelPurchase(productId, request));
    }
}

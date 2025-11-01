package com.sparta.project.domain.refund.controller;

import com.sparta.project.domain.refund.dto.RefundCreateRequest;
import com.sparta.project.domain.refund.dto.RefundProcessRequest;
import com.sparta.project.domain.refund.dto.RefundResponse;
import com.sparta.project.domain.refund.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 환불 요청
     * POST /api/refunds
     */
    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(
            @Valid @RequestBody RefundCreateRequest request) {
        RefundResponse response = refundService.createRefund(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 환불 처리 (관리자용) - 승인/거절
     * PATCH /api/refunds/{refundId}
     * 승인: {"status": "APPROVED"}
     * 거절: {"status": "REJECTED", "rejectionReason": "..."}
     */
    @PatchMapping("/{refundId}")
    public ResponseEntity<RefundResponse> processRefund(
            @PathVariable Long refundId,
            @Valid @RequestBody RefundProcessRequest request) {
        RefundResponse response = refundService.processRefund(refundId, request);
        return ResponseEntity.ok(response);
    }


    /**
     * 특정 사용자의 환불 목록 조회
     * GET /api/refunds/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundResponse>> getUserRefunds(
            @PathVariable Long userId) {
        List<RefundResponse> refunds = refundService.getUserRefunds(userId);
        return ResponseEntity.ok(refunds);
    }

}
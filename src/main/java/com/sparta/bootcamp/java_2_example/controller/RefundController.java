package com.sparta.bootcamp.java_2_example.controller;

import com.sparta.bootcamp.java_2_example.common.enums.RefundStatus;
import com.sparta.bootcamp.java_2_example.dto.request.RefundRequest;
import com.sparta.bootcamp.java_2_example.dto.response.RefundResponse;
import com.sparta.bootcamp.java_2_example.service.RefundService;
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
     * 환불 요청 생성
     */
    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(@Valid @RequestBody RefundRequest request) {
        RefundResponse response = refundService.createRefund(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 환불 요청 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefund(@PathVariable Long id) {
        RefundResponse response = refundService.getRefund(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자별 환불 요청 목록 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUser(@PathVariable Long userId) {
        List<RefundResponse> responses = refundService.getRefundsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 사용자별 환불 요청 목록 조회 (상태별)
     */
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUserAndStatus(
            @PathVariable Long userId,
            @PathVariable RefundStatus status) {
        List<RefundResponse> responses = refundService.getRefundsByUserAndStatus(userId, status);
        return ResponseEntity.ok(responses);
    }

    /**
     * 환불 승인
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<RefundResponse> approveRefund(@PathVariable Long id) {
        RefundResponse response = refundService.approveRefund(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 환불 거절
     */
    @PostMapping("/{id}/reject")
    public ResponseEntity<RefundResponse> rejectRefund(@PathVariable Long id) {
        RefundResponse response = refundService.rejectRefund(id);
        return ResponseEntity.ok(response);
    }
}

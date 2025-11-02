package com.sparta.practice.domain.refund.controller;

import com.sparta.practice.domain.refund.dto.RefundCreateRequest;
import com.sparta.practice.domain.refund.dto.RefundResponse;
import com.sparta.practice.domain.refund.entity.RefundStatus;
import com.sparta.practice.domain.refund.service.RefundService;
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

    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(@Valid @RequestBody RefundCreateRequest request) {
        RefundResponse response = refundService.createRefund(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefund(@PathVariable Long id) {
        RefundResponse response = refundService.getRefund(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) RefundStatus status
    ) {
        List<RefundResponse> responses;
        if (status != null) {
            responses = refundService.getRefundsByUserIdAndStatus(userId, status);
        } else {
            responses = refundService.getRefundsByUserId(userId);
        }
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<RefundResponse> approveRefund(@PathVariable Long id) {
        RefundResponse response = refundService.approveRefund(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<RefundResponse> rejectRefund(@PathVariable Long id) {
        RefundResponse response = refundService.rejectRefund(id);
        return ResponseEntity.ok(response);
    }
}

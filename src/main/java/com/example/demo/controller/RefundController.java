package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.controller.dto.RefundProcessRequest;
import com.example.demo.controller.dto.RefundRequest;
import com.example.demo.controller.dto.RefundResponse;
import com.example.demo.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "환불 관리", description = "환불 요청, 처리, 조회 API")
@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @Operation(summary = "환불 요청", description = "사용자가 특정 주문에 대해 환불을 요청합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<RefundResponse>> requestRefund(@Valid @RequestBody RefundRequest request) {
        try {
            RefundResponse response = refundService.requestRefund(request);
            return ApiResponse.success(response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error("INVALID_REQUEST", e.getMessage());
        }
    }

    @Operation(summary = "환불 처리", description = "관리자가 환불 요청을 승인하거나 거절합니다. 승인 시 재고가 복원됩니다.")
    @PutMapping("/{id}/process")
    public ResponseEntity<ApiResponse<RefundResponse>> processRefund(
            @PathVariable Long id,
            @Valid @RequestBody RefundProcessRequest request) {
        try {
            RefundResponse response = refundService.processRefund(id, request);
            return ApiResponse.success(response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error("INVALID_REQUEST", e.getMessage());
        }
    }

    @Operation(summary = "환불 상세 조회", description = "특정 환불 요청의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RefundResponse>> getRefund(@PathVariable Long id) {
        try {
            RefundResponse response = refundService.getRefund(id);
            return ApiResponse.success(response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error("NOT_FOUND", e.getMessage());
        }
    }

    @Operation(summary = "사용자 환불 목록 조회", description = "특정 사용자의 환불 요청 목록을 조회합니다.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<RefundResponse>>> getUserRefunds(@PathVariable Long userId) {
        try {
            List<RefundResponse> response = refundService.getUserRefunds(userId);
            return ApiResponse.success(response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error("NOT_FOUND", e.getMessage());
        }
    }
}


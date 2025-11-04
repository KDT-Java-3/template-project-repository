package com.sparta.demo1.domain.controller;

import com.sparta.demo1.domain.dto.request.RefundCreateRequest;
import com.sparta.demo1.domain.dto.response.RefundResponse;
import com.sparta.demo1.domain.entity.RefundStatus;
import com.sparta.demo1.domain.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Refund", description = "환불 관리 API")
@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

  private final RefundService refundService;

  @Operation(summary = "환불 요청", description = "완료된 주문에 대해 환불을 요청합니다.")
  @PostMapping
  public ResponseEntity<RefundResponse> createRefund(@Valid @RequestBody RefundCreateRequest request) {
    RefundResponse response = refundService.createRefund(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "환불 상세 조회", description = "특정 환불 요청의 상세 정보를 조회합니다.")
  @GetMapping("/{refundId}")
  public ResponseEntity<RefundResponse> getRefund(@PathVariable Long refundId) {
    RefundResponse response = refundService.getRefund(refundId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자별 환불 목록 조회", description = "특정 사용자의 모든 환불 요청을 조회합니다.")
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RefundResponse>> getRefundsByUser(@PathVariable Long userId) {
    List<RefundResponse> responses = refundService.getRefundsByUser(userId);
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "상태별 환불 목록 조회", description = "특정 상태의 모든 환불 요청을 조회합니다.")
  @GetMapping("/status/{status}")
  public ResponseEntity<List<RefundResponse>> getRefundsByStatus(@PathVariable RefundStatus status) {
    List<RefundResponse> responses = refundService.getRefundsByStatus(status);
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "환불 승인", description = "대기 중인 환불 요청을 승인하고 재고를 복구합니다.")
  @PostMapping("/{refundId}/approve")
  public ResponseEntity<RefundResponse> approveRefund(@PathVariable Long refundId) {
    RefundResponse response = refundService.approveRefund(refundId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "환불 거부", description = "대기 중인 환불 요청을 거부합니다.")
  @PostMapping("/{refundId}/reject")
  public ResponseEntity<RefundResponse> rejectRefund(@PathVariable Long refundId) {
    RefundResponse response = refundService.rejectRefund(refundId);
    return ResponseEntity.ok(response);
  }
}
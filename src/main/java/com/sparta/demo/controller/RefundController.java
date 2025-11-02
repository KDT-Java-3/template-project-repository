package com.sparta.demo.controller;

import com.sparta.demo.domain.refund.RefundStatus;
import com.sparta.demo.dto.refund.*;
import com.sparta.demo.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Refund", description = "환불 관리 API")
@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @Operation(summary = "환불 요청", description = "새로운 환불 요청을 생성합니다.")
    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(@Valid @RequestBody RefundRequest request) {
        RefundCreateDto dto = RefundCreateDto.from(request);
        RefundDto refundDto = refundService.createRefund(dto);
        RefundResponse response = RefundResponse.from(refundDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "환불 단건 조회", description = "ID로 환불 요청을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.getRefund(id);
        RefundResponse response = RefundResponse.from(refundDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자별 환불 조회", description = "특정 사용자의 모든 환불 요청을 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUserId(@PathVariable Long userId) {
        List<RefundDto> refundDtos = refundService.getRefundsByUserId(userId);
        List<RefundResponse> responses = refundDtos.stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사용자별 환불 상태 조회", description = "특정 사용자의 특정 상태 환불 요청을 조회합니다.")
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUserIdAndStatus(
            @PathVariable Long userId,
            @PathVariable RefundStatus status) {
        List<RefundDto> refundDtos = refundService.getRefundsByUserIdAndStatus(userId, status);
        List<RefundResponse> responses = refundDtos.stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "환불 승인", description = "환불 요청을 승인하고 재고를 복원합니다.")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<RefundResponse> approveRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.approveRefund(id);
        RefundResponse response = RefundResponse.from(refundDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "환불 거절", description = "환불 요청을 거절합니다.")
    @PatchMapping("/{id}/reject")
    public ResponseEntity<RefundResponse> rejectRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.rejectRefund(id);
        RefundResponse response = RefundResponse.from(refundDto);
        return ResponseEntity.ok(response);
    }
}

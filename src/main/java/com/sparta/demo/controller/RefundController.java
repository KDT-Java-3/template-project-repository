package com.sparta.demo.controller;

import com.sparta.demo.common.ApiResponse;
import com.sparta.demo.controller.dto.refund.RefundRequest;
import com.sparta.demo.controller.dto.refund.RefundResponse;
import com.sparta.demo.controller.mapper.RefundControllerMapper;
import com.sparta.demo.domain.refund.RefundStatus;
import com.sparta.demo.service.RefundService;
import com.sparta.demo.service.dto.refund.RefundDto;
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
    private final RefundControllerMapper mapper;

    @Operation(summary = "환불 요청", description = "새로운 환불 요청을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<RefundResponse>> createRefund(@Valid @RequestBody RefundRequest request) {
        var createDto = mapper.toCreateDto(request);
        RefundDto refundDto = refundService.createRefund(createDto);
        RefundResponse response = mapper.toResponse(refundDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @Operation(summary = "환불 단건 조회", description = "ID로 환불 요청을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RefundResponse>> getRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.getRefund(id);
        RefundResponse response = mapper.toResponse(refundDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "환불 목록 조회", description = "환불 목록을 조회합니다. Query Parameter로 필터링 가능: user-id, status")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RefundResponse>>> getRefunds(
            @RequestParam(name = "user-id", required = false) Long userId,
            @RequestParam(required = false) RefundStatus status) {
        List<RefundDto> refundDtos;

        if (userId != null && status != null) {
            refundDtos = refundService.getRefundsByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            refundDtos = refundService.getRefundsByUserId(userId);
        } else {
            throw new IllegalArgumentException("user-id 파라미터는 필수입니다.");
        }

        List<RefundResponse> responses = refundDtos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @Operation(summary = "환불 승인", description = "환불 요청을 승인하고 재고를 복원합니다.")
    @PostMapping("/{id}/approval")
    public ResponseEntity<ApiResponse<RefundResponse>> approveRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.approveRefund(id);
        RefundResponse response = mapper.toResponse(refundDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "환불 거절", description = "환불 요청을 거절합니다.")
    @PostMapping("/{id}/rejection")
    public ResponseEntity<ApiResponse<RefundResponse>> rejectRefund(@PathVariable Long id) {
        RefundDto refundDto = refundService.rejectRefund(id);
        RefundResponse response = mapper.toResponse(refundDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

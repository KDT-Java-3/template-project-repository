package com.sprata.sparta_ecommerce.controller;

import com.sprata.sparta_ecommerce.dto.ChangeRefundStatusRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundResponseDto;
import com.sprata.sparta_ecommerce.entity.RefundStatus;
import com.sprata.sparta_ecommerce.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sprata.sparta_ecommerce.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<?>> requestRefund(@Valid @RequestBody RefundRequestDto refundRequestDto) {
        RefundResponseDto responseDto = refundService.requestRefund(refundRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseDto, "환불 요청 성공"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<?>> getRefundsByUserId(@PathVariable Long userId) {
        List<RefundResponseDto> responseDtos = refundService.getRefundsByUserId(userId);
        return ResponseEntity.ok(ResponseDto.success(responseDtos, "환불 목록 조회 성공"));
    }

    @PatchMapping("/{refundId}/admin")
    public ResponseEntity<ResponseDto<?>> processRefund(@PathVariable Long refundId, @Valid ChangeRefundStatusRequestDto requestDto) {
        RefundStatus status = RefundStatus.find(requestDto);
        RefundResponseDto responseDto = refundService.processRefund(refundId, status);
        return ResponseEntity.ok(ResponseDto.success(responseDto, "환불 처리 성공"));
    }
}
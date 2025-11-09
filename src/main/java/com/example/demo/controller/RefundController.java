package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.controller.dto.RefundRequest;
import com.example.demo.controller.dto.RefundResponse;
import com.example.demo.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchases/{purchaseId}/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    // [실습] 구매 ID와 환불 사유를 받아 환불 트랜잭션을 실행한다.
    @PostMapping
    public ResponseEntity<ApiResponse<RefundResponse>> processRefund(
            @PathVariable Long purchaseId,
            @Valid @RequestBody RefundRequest request
    ) {
        RefundResponse response = refundService.processRefund(purchaseId, request);
        return ApiResponse.created(response);
    }
}

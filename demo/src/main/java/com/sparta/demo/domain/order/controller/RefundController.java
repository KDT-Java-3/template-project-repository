package com.sparta.demo.domain.order.controller;

import com.sparta.demo.domain.order.dto.request.CreateRefundRequest;
import com.sparta.demo.domain.order.dto.request.ProcessRefundRequest;
import com.sparta.demo.domain.order.dto.response.RefundResponse;
import com.sparta.demo.domain.order.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundController {
    private final RefundService refundService;

    // 환불 요청
    @PostMapping
    public RefundResponse createRefund(@Valid @RequestBody CreateRefundRequest request) throws Exception {
        return refundService.createRefund(request);
    }

    // 환불 처리 (승인/거절)
    @PutMapping("/process")
    public RefundResponse processRefund(@Valid @RequestBody ProcessRefundRequest request) throws Exception {
        return refundService.processRefund(request);
    }

    // 환불 조회 (사용자별)
    @GetMapping("/user/{userId}")
    public List<RefundResponse> getRefundsByUserId(@PathVariable Long userId) {
        return refundService.getRefundsByUserId(userId);
    }
}


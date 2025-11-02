package com.example.week01_project.web;

import com.example.week01_project.common.ApiResponse;
import com.example.week01_project.domain.refund.Refund;
import com.example.week01_project.dto.refund.RefundDtos.*;
import com.example.week01_project.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {
    private final RefundService refundService;

    @PostMapping
    public ApiResponse<Resp> request(@RequestBody @Valid RequestReq req) {
        return ApiResponse.ok(refundService.request(req));
    }

    // 환불 항목 추가 (부분 환불 시 사용)
    @PostMapping("/{refundId}/items")
    public ApiResponse<String> addItem(@PathVariable Long refundId,
                                       @RequestParam Long orderItemId,
                                       @RequestParam Integer quantity,
                                       @RequestParam BigDecimal amount) {
        refundService.addRefundItem(refundId, orderItemId, quantity, amount);
        return ApiResponse.ok("ok");
    }

    // 승인/거절 처리
    @PostMapping("/{refundId}/process")
    public ApiResponse<Resp> process(@PathVariable Long refundId, @RequestBody @Valid ProcessReq req) {
        return ApiResponse.ok(refundService.process(refundId, req));
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<List<Refund>> listByUser(@PathVariable Long userId) {
        return ApiResponse.ok(refundService.listByUser(userId));
    }
}

package com.sparta.sangmin.controller;

import com.sparta.sangmin.controller.dto.RefundProcessRequest;
import com.sparta.sangmin.controller.dto.RefundRequest;
import com.sparta.sangmin.controller.dto.RefundResponse;
import com.sparta.sangmin.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponse> requestRefund(@RequestBody RefundRequest request) {
        RefundResponse response = refundService.requestRefund(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByUserId(@PathVariable Long userId) {
        List<RefundResponse> refunds = refundService.getRefundsByUserId(userId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefundById(@PathVariable Long id) {
        RefundResponse response = refundService.getRefundById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<RefundResponse> processRefund(
            @PathVariable Long id,
            @RequestBody RefundProcessRequest request) {

        RefundResponse response;
        if ("approve".equalsIgnoreCase(request.action())) {
            response = refundService.approveRefund(id);
        } else if ("reject".equalsIgnoreCase(request.action())) {
            response = refundService.rejectRefund(id);
        } else {
            throw new IllegalArgumentException("유효하지 않은 처리 요청입니다: " + request.action());
        }

        return ResponseEntity.ok(response);
    }
}

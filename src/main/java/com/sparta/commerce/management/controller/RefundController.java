package com.sparta.commerce.management.controller;

import com.sparta.commerce.management.dto.request.refund.RefundCreateRequest;
import com.sparta.commerce.management.dto.request.refund.RefundSearchRequest;
import com.sparta.commerce.management.dto.request.refund.RefundUpdateRequest;
import com.sparta.commerce.management.dto.response.purchase.PurchaseResponse;
import com.sparta.commerce.management.dto.response.refund.RefundResponse;
import com.sparta.commerce.management.entity.Refund;
import com.sparta.commerce.management.service.RefundService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refund")
public class RefundController {

    private final RefundService refundService;


    //환불 요청 API
    @PostMapping
    public ResponseEntity<RefundResponse> save(@RequestBody RefundCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refundService.save(request));
    }

    //환불 처리 API
    @PutMapping
    public ResponseEntity<RefundResponse> update(@RequestBody RefundUpdateRequest request) {
        return ResponseEntity.ok(refundService.update(request));
    }

    //환불 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<List<RefundResponse>> findAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(refundService.findAllByUserId(userId));
    }
}

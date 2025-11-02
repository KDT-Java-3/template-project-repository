package com.sparta.ecommerce.domain.refund.controller;

import com.sparta.ecommerce.domain.refund.dto.RefundCreateRequest;
import com.sparta.ecommerce.domain.refund.dto.RefundResponse;
import com.sparta.ecommerce.domain.refund.dto.RefundUpdateRequest;
import com.sparta.ecommerce.domain.refund.service.RefundService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/refunds"})
public class RefundController {
    private final RefundService refundService;

    @PostMapping
    ResponseEntity<RefundResponse> createRefund(@RequestBody RefundCreateRequest dto) {
        RefundResponse res = this.refundService.createRefund(dto);
        return ResponseEntity.ok(res);
    }

    @GetMapping({"/{id}"})
    ResponseEntity<List<RefundResponse>> readRefundsByUser(Long id) {
        List<RefundResponse> res = this.refundService.readRefundByUser(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping
    ResponseEntity<RefundResponse> updateRefundStatus(@RequestBody RefundUpdateRequest dto) {
        RefundResponse res = this.refundService.updateRefundProcess(dto);
        return ResponseEntity.ok(res);
    }

    @Generated
    public RefundController(final RefundService refundService) {
        this.refundService = refundService;
    }
}

package com.wodydtns.commerce.domain.refund.controller;

import com.wodydtns.commerce.domain.refund.dto.CreateRefundRequest;
import com.wodydtns.commerce.domain.refund.dto.SearchRefundResponse;
import com.wodydtns.commerce.domain.refund.dto.UpdateRefundRequest;
import com.wodydtns.commerce.domain.refund.service.RefundService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<HttpStatus> createRefund(@RequestBody CreateRefundRequest refundRequest) {
        refundService.createRefund(refundRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchRefundResponse> searchRefund(@PathVariable long id) {
        SearchRefundResponse refund = refundService.searchRefund(id);
        return ResponseEntity.ok(refund);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateRefund(@RequestBody UpdateRefundRequest refundRequest) {
        refundService.updateRefund(refundRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

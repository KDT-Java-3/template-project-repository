package com.example.demo.domain.refund.controller;

import com.example.demo.domain.refund.dto.RefundRequestDto;
import com.example.demo.domain.refund.dto.RefundResponseDto;
import com.example.demo.domain.refund.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping
    public ResponseEntity<RefundResponseDto> createRefund(@Valid @RequestBody RefundRequestDto request){
        RefundResponseDto response = refundService.createRefund(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/{refundId}/complete")
    public ResponseEntity<Void> completeRefund(@PathVariable Long userId, @PathVariable Long refundId){
        refundService.completeRefund(userId, refundId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/{refundId}/cancel")
    public ResponseEntity<Void> cancelRefund(@PathVariable Long userId, @PathVariable Long refundId){
        refundService.cancelRefund(userId, refundId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RefundResponseDto>> getAllRefunds(){
        List<RefundResponseDto> response = refundService.getAllRefunds();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<List<RefundResponseDto>> getRefund(@PathVariable Long refundId){
        List<RefundResponseDto> response = refundService.getRefundsById(refundId);
        return ResponseEntity.ok(response);
    }
}

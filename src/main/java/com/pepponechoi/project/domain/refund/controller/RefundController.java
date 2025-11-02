package com.pepponechoi.project.domain.refund.controller;

import com.pepponechoi.project.domain.refund.dto.request.RefundCreateRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundReadRequest;
import com.pepponechoi.project.domain.refund.dto.request.RefundUpdateRequest;
import com.pepponechoi.project.domain.refund.dto.response.RefundResponse;
import com.pepponechoi.project.domain.refund.service.RefundService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<RefundResponse> createRefund(@RequestBody RefundCreateRequest request) {
        RefundResponse response = refundService.createRefund(request);
        return ResponseEntity.created(URI.create(String.format("/api/refunds/%d", response.id()))).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RefundResponse>> getRefundsByUser(@ModelAttribute RefundReadRequest request) {
        List<RefundResponse> responses = refundService.getRefundsByUser(request);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateRefund(
        @RequestBody RefundUpdateRequest request,
        @PathVariable Long id) {
        Boolean response = refundService.update(id, request);
        return ResponseEntity.ok(response);
    }
}

package com.sparta.ecommerce.refund.presentation;

import static com.sparta.ecommerce.refund.application.dto.RefundDto.*;

import com.sparta.ecommerce.refund.application.RefundService;
import com.sparta.ecommerce.refund.application.dto.RefundDto;
import com.sparta.ecommerce.refund.domain.RefundStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/refunds")
@RestController
@RequiredArgsConstructor
public class RefundApiController {

    private final RefundService refundService;

    // 환불 요청 API
    @PostMapping
    public ResponseEntity<RefundResponse> createRefund
    (
            @RequestBody @Valid RefundCreateRequest createRequest
    ) {
        RefundResponse response = refundService.createRefund(createRequest);
        return ResponseEntity.ok(response);
    }

    // 환불 처리 API
    @PatchMapping("/{id}")
    public ResponseEntity<RefundResponse> changeStatus
    (
            @PathVariable Long id,
            @RequestParam RefundStatus status
    )
    {
        RefundResponse result = refundService.changeStatus(id, status);
        return ResponseEntity.ok(result);
    }

    // 환불 조회 API
    @GetMapping
    public ResponseEntity<List<RefundResponse>> getUserRefunds(@RequestParam Long userId) {
        List<RefundResponse> result = refundService.getUserRefunds(userId);
        return ResponseEntity.ok(result);
    }


}

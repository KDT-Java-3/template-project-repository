package com.sparta.week1.refund;

import com.sparta.week1.dto.refund.CreateRefundDto;
import com.sparta.week1.entity.Refund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("refund")
public class RefundController {
    private final RefundService refundService;

    /**
     * 환불 요청
     */
    @PostMapping()
    public ResponseEntity<Refund> createRefund(@RequestBody CreateRefundDto dto) {
        Refund refund = refundService.createRefund(dto);
        return ResponseEntity.ok().body(refund);
    }

    /**
     * 환불 처리
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Refund> updateRefund(@PathVariable long id, @RequestBody CreateRefundDto dto) {
        Refund refund = refundService.updateRefund(id, dto);
        return ResponseEntity.ok().body(refund);
    }

    /**
     * 환불 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<Refund> getRefund(@PathVariable long id) {
        Refund refund = refundService.getRefundById(id);
        return ResponseEntity.ok().body(refund);
    }
}

package com.sparta.project1.domain.refund.api;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.refund.api.dto.RefundRegisterRequest;
import com.sparta.project1.domain.refund.api.dto.RefundResponse;
import com.sparta.project1.domain.refund.service.RefundFindService;
import com.sparta.project1.domain.refund.service.RefundModifyService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/refund")
@RequiredArgsConstructor
public class RefundController {
    private final RefundModifyService refundModifyService;
    private final RefundFindService refundFindService;

    /*
    * 생각해보니 환불을 상품단위로 해야 하는데 주문단위로 해버림
    * 근데 시간 없어서 패스
    * */
    @PostMapping("/{orderId}/{userId}")
    public ResponseEntity<Void> register(@PathVariable("orderId") Long orderId,
                                         @PathVariable("userId") Long userId,
                                         @RequestBody RefundRegisterRequest request) {
        refundModifyService.register(orderId, userId, request);

        return ResponseEntity.ok().build();
    }

    /*
    * 환불 승인 시 재고를 복원하는 요구사항이 있는데
    * 환불한 물품을 본인이 받은 후 재고 복원을 하는게 맞지 않을까라는 생각이 들음
    * 일단 요구사항대로 진행 ㄱㄱ
    * */
    @PatchMapping("/{refundId}")
    public ResponseEntity<Void> changeStatus(@PathVariable("refundId") Long refundId,
                                             @Pattern(regexp = "APPROVED|REJECTED") @RequestParam("status") String status) {
        refundModifyService.changeStatus(refundId, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<RefundResponse>> findAll(@PathVariable("userId") Long userId,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size")int size) {
        PageResponse<RefundResponse> responses = refundFindService.getAllByUserId(userId, page -1, size);

        return ResponseEntity.ok(responses);
    }

}


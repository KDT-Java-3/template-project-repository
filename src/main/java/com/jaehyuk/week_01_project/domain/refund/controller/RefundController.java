package com.jaehyuk.week_01_project.domain.refund.controller;

import com.jaehyuk.week_01_project.config.auth.LoginUser;
import com.jaehyuk.week_01_project.domain.refund.dto.CreateRefundRequest;
import com.jaehyuk.week_01_project.domain.refund.dto.ProcessRefundRequest;
import com.jaehyuk.week_01_project.domain.refund.dto.RefundResponse;
import com.jaehyuk.week_01_project.domain.refund.enums.RefundStatus;
import com.jaehyuk.week_01_project.domain.refund.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refund")
@Tag(name = "Refund", description = "환불 요청 관리 API")
public class RefundController {
    private final RefundService refundService;

    @Operation(
            summary = "환불 목록 조회",
            description = """
                    로그인한 사용자의 환불 요청 목록을 조회합니다. (로그인 필요)

                    - 환불 상태별 필터링 가능 (status 파라미터)
                    - 조회 가능한 상태: REQUESTED, APPROVED, REJECTED
                    - 각 환불 요청에는 주문 정보, 사유, 요청 날짜가 포함됩니다

                    예시:
                    - GET /api/refund/v1 (전체 환불 조회)
                    - GET /api/refund/v1?status=APPROVED (승인된 환불만 조회)
                    - GET /api/refund/v1?status=REQUESTED (대기 중인 환불만 조회)
                    """
    )
    @GetMapping("/v1")
    public ResponseEntity<List<RefundResponse>> getRefunds(
            @LoginUser Long userId,
            @RequestParam(required = false) RefundStatus status
    ) {
        log.debug("환불 조회 요청 - userId: {}, status: {}", userId, status);

        List<RefundResponse> refunds = refundService.getRefunds(userId, status);

        return ResponseEntity.ok(refunds);
    }

    @Operation(
            summary = "환불 요청",
            description = """
                    주문에 대한 환불을 요청합니다. (로그인 필요)

                    - 본인의 주문만 환불 요청 가능합니다
                    - PENDING 상태의 주문만 환불 요청 가능합니다
                    - 환불 요청 시에는 재고가 복원되지 않습니다 (승인 시에만 복원)
                    - 주문 상태가 REFUND_REQUESTED로 변경됩니다
                    - 환불 요청 상세 정보를 반환합니다

                    환불 요청 조건:
                    - 주문 상태: PENDING
                    - 소유자: 본인

                    예시 요청:
                    {
                      "purchaseId": 1,
                      "reason": "상품 불량으로 인한 환불 요청"
                    }
                    """
    )
    @PostMapping("/v1")
    public ResponseEntity<RefundResponse> createRefund(
            @LoginUser Long userId,
            @Valid @RequestBody CreateRefundRequest request
    ) {
        log.debug("환불 요청 - userId: {}, purchaseId: {}, reason: {}",
                userId, request.purchaseId(), request.reason());

        RefundResponse response = refundService.createRefund(userId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "환불 처리",
            description = """
                    환불 요청을 승인하거나 거절합니다. (로그인 필요)

                    - REQUESTED 상태의 환불 요청만 처리 가능합니다
                    - 승인 시: 재고를 복원하고, 환불 상태를 APPROVED로, 주문 상태를 REFUNDED로 변경
                    - 거절 시: 환불 상태를 REJECTED로, 주문 상태를 PENDING으로 되돌림 (재고는 변경 없음)

                    처리 액션:
                    - APPROVE: 환불 승인
                    - REJECT: 환불 거절

                    예시 요청:
                    {
                      "action": "APPROVE"
                    }
                    """
    )
    @PatchMapping("/v1/{refundId}/process")
    public ResponseEntity<RefundResponse> processRefund(
            @PathVariable Long refundId,
            @LoginUser Long userId,
            @Valid @RequestBody ProcessRefundRequest request
    ) {
        log.debug("환불 처리 요청 - refundId: {}, userId: {}, action: {}",
                refundId, userId, request.action());

        RefundResponse response = refundService.processRefund(refundId, request);

        return ResponseEntity.ok(response);
    }
}

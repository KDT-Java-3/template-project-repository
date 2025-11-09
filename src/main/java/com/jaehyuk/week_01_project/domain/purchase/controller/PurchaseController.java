package com.jaehyuk.week_01_project.domain.purchase.controller;

import com.jaehyuk.week_01_project.config.auth.LoginUser;
import com.jaehyuk.week_01_project.domain.purchase.dto.CreatePurchaseRequest;
import com.jaehyuk.week_01_project.domain.purchase.dto.PurchaseResponse;
import com.jaehyuk.week_01_project.domain.purchase.dto.UpdatePurchaseStatusRequest;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import com.jaehyuk.week_01_project.domain.purchase.service.PurchaseService;
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
@RequestMapping("/api/purchase")
@Tag(name = "Purchase", description = "주문 관리 API")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Operation(
            summary = "주문 조회",
            description = """
                    로그인한 사용자의 주문 목록을 조회합니다. (로그인 필요)

                    - 주문 상태별 필터링 가능 (status 파라미터)
                    - 조회 가능한 상태: PENDING, COMPLETED, CANCELED
                    - 각 주문에는 상품 상세 정보가 포함됩니다

                    예시:
                    - GET /api/purchase/v1 (전체 주문 조회)
                    - GET /api/purchase/v1?status=COMPLETED (완료된 주문만 조회)
                    """
    )
    @GetMapping("/v1")
    public ResponseEntity<List<PurchaseResponse>> getPurchases(
            @LoginUser Long userId,
            @RequestParam(required = false) PurchaseStatus status
    ) {
        log.debug("주문 조회 요청 - userId: {}, status: {}", userId, status);

        List<PurchaseResponse> purchases = purchaseService.getPurchases(userId, status);

        return ResponseEntity.ok(purchases);
    }

    @Operation(
            summary = "주문 생성",
            description = """
                    새로운 주문을 생성합니다. (로그인 필요)

                    - 여러 상품을 한 번에 주문 가능 (장바구니 기능)
                    - 상품 재고가 자동으로 감소됩니다
                    - 재고가 부족한 경우 주문이 실패합니다
                    - 초기 주문 상태는 PENDING으로 생성됩니다
                    - 생성된 주문의 ID를 반환합니다

                    예시 요청:
                    {
                      "shippingAddress": "서울시 강남구 테헤란로 123",
                      "items": [
                        { "productId": 1, "quantity": 2 },
                        { "productId": 3, "quantity": 1 }
                      ]
                    }
                    """
    )
    @PostMapping("/v1")
    public ResponseEntity<Long> createPurchase(
            @LoginUser Long userId,
            @Valid @RequestBody CreatePurchaseRequest request
    ) {
        log.debug("주문 생성 요청 - userId: {}, shippingAddress: {}, itemCount: {}",
                userId, request.shippingAddress(), request.items().size());

        Long purchaseId = purchaseService.createPurchase(userId, request);

        return ResponseEntity.ok(purchaseId);
    }

    @Operation(
            summary = "주문 상태 변경",
            description = """
                    주문의 상태를 변경합니다. (로그인 필요)

                    - 본인의 주문만 수정 가능합니다
                    - PENDING 상태에서만 변경 가능합니다
                    - COMPLETED 또는 CANCELED로만 변경 가능합니다
                    - 변경된 주문의 ID를 반환합니다

                    상태 변경 규칙:
                    - PENDING → COMPLETED (주문 완료)
                    - PENDING → CANCELED (주문 취소)

                    예시 요청:
                    {
                      "status": "COMPLETED"
                    }
                    """
    )
    @PatchMapping("/v1/{purchaseId}/status")
    public ResponseEntity<Long> updatePurchaseStatus(
            @PathVariable Long purchaseId,
            @LoginUser Long userId,
            @Valid @RequestBody UpdatePurchaseStatusRequest request
    ) {
        log.debug("주문 상태 변경 요청 - purchaseId: {}, userId: {}, newStatus: {}",
                purchaseId, userId, request.status());

        Long updatedPurchaseId = purchaseService.updatePurchaseStatus(purchaseId, userId, request);

        return ResponseEntity.ok(updatedPurchaseId);
    }

    @Operation(
            summary = "주문 취소",
            description = """
                    주문을 취소하고 재고를 자동으로 복원합니다. (로그인 필요)

                    - 본인의 주문만 취소 가능합니다
                    - PENDING 상태의 주문만 취소 가능합니다
                    - 주문에 포함된 모든 상품의 재고가 자동으로 복원됩니다
                    - 주문 상태가 CANCELED로 변경됩니다
                    - 취소된 주문의 ID를 반환합니다

                    취소 조건:
                    - 현재 상태: PENDING
                    - 소유자: 본인
                    """
    )
    @PostMapping("/v1/{purchaseId}/cancel")
    public ResponseEntity<Long> cancelPurchase(
            @PathVariable Long purchaseId,
            @LoginUser Long userId
    ) {
        log.debug("주문 취소 요청 - purchaseId: {}, userId: {}", purchaseId, userId);

        Long canceledPurchaseId = purchaseService.cancelPurchase(purchaseId, userId);

        return ResponseEntity.ok(canceledPurchaseId);
    }
}

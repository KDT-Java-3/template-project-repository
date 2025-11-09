package com.jaehyuk.week_01_project.domain.purchase.dto;

import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * 주문 상태 변경 요청 DTO
 *
 * @param status 변경할 상태 (COMPLETED 또는 CANCELED만 가능)
 */
@Builder
public record UpdatePurchaseStatusRequest(
        @NotNull(message = "주문 상태는 필수입니다")
        PurchaseStatus status
) {
}

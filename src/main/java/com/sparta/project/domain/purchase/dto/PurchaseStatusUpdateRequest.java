package com.sparta.project.domain.purchase.dto;

import com.sparta.project.common.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseStatusUpdateRequest {

    @NotNull(message = "변경할 주문 상태는 필수입니다.")
    private PurchaseStatus status;
}
package com.sparta.ecommerce.refund.application.dto;

import com.sparta.ecommerce.purchase.domain.Purchase;
import com.sparta.ecommerce.refund.domain.Refund;
import com.sparta.ecommerce.refund.domain.RefundStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RefundDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundCreateRequest {
        @NotNull(message = "회원 일련번호는 필수입니다.")
        private Long userId;

        @NotNull(message = "주문 번호는 필수입니다.")
        private Long purchaseId;

        @NotNull(message = "사유는 필수입니다.")
        private String reason;

        public Refund toEntity(Purchase purchase) {
            return Refund.builder()
                    .userId(this.userId)
                    .purchase(purchase)
                    .reason(this.reason)
                    .status(RefundStatus.PENDING)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundResponse {
        private Long id;
        private String reason;
        private LocalDateTime createdAt;

        public static RefundResponse fromEntity(Refund refund) {
            return new RefundResponse(refund.getId(), refund.getReason(), refund.getCreatedAt());
        }
    }
}

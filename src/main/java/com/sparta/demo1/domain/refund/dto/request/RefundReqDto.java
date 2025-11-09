package com.sparta.demo1.domain.refund.dto.request;

import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import com.sparta.demo1.domain.refund.enums.RefundStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RefundReqDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RefundFilterDto {
        private Long userId;
        private RefundStatus refundStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RefundCreateDto {
        @NotBlank(message = "유저 ID는 필수입니다.")
        private Long userId;

        @NotBlank(message = "주문 ID는 필수입니다.")
        private Long purchaseId;

        @NotBlank(message = "환불 이유를 입력해주세요.")
        private String reason;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RefundProcessing {
        @NotBlank(message = "사용자 ID는 필수입니다.")
        private Long userId;

        @NotBlank(message = "주문 ID는 필수입니다.")
        private Long purchaseId;
    }
}

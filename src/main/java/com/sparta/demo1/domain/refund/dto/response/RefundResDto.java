package com.sparta.demo1.domain.refund.dto.response;

import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class RefundResDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RefundInfo{
        private Long id;
        private String reason;

        @Builder
        public RefundInfo(Long id, String reason) {
            this.id = id;
            this.reason = reason;
        }
    }
}

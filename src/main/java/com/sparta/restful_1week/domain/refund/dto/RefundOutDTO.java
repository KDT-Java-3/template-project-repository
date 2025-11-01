package com.sparta.restful_1week.domain.refund.dto;

import com.sparta.restful_1week.domain.refund.entity.Refund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundOutDTO {
    private Long id;
    private String userId;
    private String orderId;
    private String reason;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public RefundOutDTO(Refund refund) {
        this.id = refund.getId();
        this.userId =  refund.getUserId();
        this.orderId = refund.getOrderId();
        this.reason = refund.getReason();
        this.createdAt = refund.getCreatedAt();
        this.updatedAt = refund.getUpdatedAt();
    }
}

package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Refund;
import com.sprata.sparta_ecommerce.entity.RefundStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RefundResponseDto {
    private Long id;
    private Long user_id;
    private Long order_id;
    private String reason;
    private RefundStatus status;
    private LocalDateTime requested_at;

    public RefundResponseDto(Refund refund) {
        this.id = refund.getId();
        this.user_id = refund.getUserId();
        this.order_id = refund.getOrder().getId();
        this.reason = refund.getReason();
        this.status = refund.getStatus();
        this.requested_at = refund.getCreatedAt();
    }
}

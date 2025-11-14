package com.example.demo.domain.refund.dto;

import com.example.demo.domain.Status;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.refund.entity.Refund;
import com.example.demo.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefundResponseDto {
    Long refundId;

    Long userId;

    Long orderId;

    String reason;

    Status status;

    public RefundResponseDto(Refund refund){
        this.refundId = refund.getId();
        this.userId = refund.getUser().getId();
        this.orderId = refund.getOrder().getId();
        this.reason = refund.getReason();
        this.status = refund.getStatus();
    }
}

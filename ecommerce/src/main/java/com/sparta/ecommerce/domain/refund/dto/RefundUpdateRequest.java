package com.sparta.ecommerce.domain.refund.dto;


import com.sparta.ecommerce.domain.refund.entity.RefundStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundUpdateRequest {
    Long refundId;
    RefundStatus status;
}
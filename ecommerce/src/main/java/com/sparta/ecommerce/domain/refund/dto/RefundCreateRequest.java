package com.sparta.ecommerce.domain.refund.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundCreateRequest {
    Long userId;
    Long orderId;
    String reason;
}


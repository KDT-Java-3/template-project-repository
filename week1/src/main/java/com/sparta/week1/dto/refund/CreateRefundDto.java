package com.sparta.week1.dto.refund;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRefundDto {
    private String reason;
    private Long userId;
    private Long orderId;
    private String status;
}

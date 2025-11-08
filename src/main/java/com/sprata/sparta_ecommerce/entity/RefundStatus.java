package com.sprata.sparta_ecommerce.entity;

import com.sprata.sparta_ecommerce.dto.ChangeRefundStatusRequestDto;
import jakarta.validation.Valid;
import org.springframework.util.StringUtils;

public enum RefundStatus {
    PENDING,    // 환불 대기
    APPROVED,   // 환불 승인
    REJECTED    // 환불 거절
    ;

    public static RefundStatus find( ChangeRefundStatusRequestDto requestDto) {
        if (StringUtils.hasText(requestDto.getRefund_status())) {
            return RefundStatus.valueOf(requestDto.getRefund_status().toUpperCase());
        } else {
            throw new IllegalArgumentException("잘못된 환불 상태 입니다.");
        }
    }

}

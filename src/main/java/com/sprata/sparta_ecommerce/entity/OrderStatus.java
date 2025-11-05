package com.sprata.sparta_ecommerce.entity;

import com.sprata.sparta_ecommerce.dto.ChangeOrderStatusRequestDto;
import org.springframework.util.StringUtils;

public enum OrderStatus {
    PENDING,    // 주문 대기
    COMPLETED,  // 주문 완료
    CANCEL_PENDING, // 취소대기
    CANCELED    // 주문 취소
    ;

    public static OrderStatus find(ChangeOrderStatusRequestDto requestDto) {
        if(StringUtils.hasText(requestDto.getOrder_status())){
            return OrderStatus.valueOf(requestDto.getOrder_status().toUpperCase());
        }else {
            throw new IllegalArgumentException("잘못된 주문 상태 입니다.");
        }
    }
}

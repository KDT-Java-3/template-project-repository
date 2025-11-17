package com.example.demo.domain.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 취소 요청 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancelRequest {

    private String cancelReason;  // 취소 사유 (선택사항)
}

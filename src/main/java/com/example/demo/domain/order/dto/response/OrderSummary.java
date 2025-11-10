package com.example.demo.domain.order.dto.response;

import com.example.demo.domain.order.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 요약 응답 DTO (목록 조회용)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {

    private Long id;
    private Long userId;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private Integer itemCount;  // 주문 항목 개수
    private BigDecimal totalAmount;  // 전체 주문 금액
    private LocalDateTime createdAt;
}

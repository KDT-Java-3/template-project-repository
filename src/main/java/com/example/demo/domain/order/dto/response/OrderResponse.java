package com.example.demo.domain.order.dto.response;

import com.example.demo.domain.order.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Long userId;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private List<OrderItemResponse> orderItems;
    private BigDecimal totalAmount;  // 전체 주문 금액
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

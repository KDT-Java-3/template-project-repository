package com.sparta.demo.dto.order;

import com.sparta.demo.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Controller에서 클라이언트에게 반환하는 주문 응답 DTO
 */
@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(OrderDto dto) {
        return new OrderResponse(
                dto.getId(),
                dto.getUserId(),
                dto.getStatus(),
                dto.getShippingAddress(),
                dto.getTotalPrice(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

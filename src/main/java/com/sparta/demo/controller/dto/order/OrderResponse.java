package com.sparta.demo.controller.dto.order;

import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.service.dto.order.OrderDto;
import com.sparta.demo.service.dto.order.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(OrderDto dto) {
        return new OrderResponse(
                dto.getId(),
                dto.getUserId(),
                dto.getStatus(),
                dto.getShippingAddress(),
                dto.getTotalPrice(),
                dto.getOrderItems(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

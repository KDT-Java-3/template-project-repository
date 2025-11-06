package com.sparta.demo.service.dto.order;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer에서 사용하는 주문 조회 DTO
 */
@Getter
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private List<OrderItemDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderDto from(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(OrderItemDto::from)
                .collect(Collectors.toList());

        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                orderItemDtos,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}

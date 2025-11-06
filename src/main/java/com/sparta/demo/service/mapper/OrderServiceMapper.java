package com.sparta.demo.service.mapper;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderItem;
import com.sparta.demo.service.dto.order.OrderDto;
import com.sparta.demo.service.dto.order.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Order Service Layer Mapper
 * Entity → DTO 변환 담당
 */
@Component
public class OrderServiceMapper {

    /**
     * Order Entity를 OrderDto로 변환
     */
    public OrderDto toDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(this::toOrderItemDto)
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

    /**
     * OrderItem Entity를 OrderItemDto로 변환
     */
    private OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}

package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    Long cancelOrder(Long orderId);
}
package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersByUserId(OrderServiceSearchDto searchDto, PageDto pageDto);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    Long cancelOrder(Long orderId);
}
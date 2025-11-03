package com.sparta.proejct1101.domain.order.service;

import com.sparta.proejct1101.domain.order.dto.request.OrderReq;
import com.sparta.proejct1101.domain.order.dto.request.OrderStatusUpdateReq;
import com.sparta.proejct1101.domain.order.dto.response.OrderRes;

import java.util.List;

public interface OrderService {

    OrderRes createOrder(OrderReq req);

    List<OrderRes> getOrdersByUserId(Long userId);

    OrderRes getOrder(Long orderId);

    OrderRes updateOrderStatus(Long orderId, OrderStatusUpdateReq req);
}

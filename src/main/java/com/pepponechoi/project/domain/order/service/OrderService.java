package com.pepponechoi.project.domain.order.service;

import com.pepponechoi.project.domain.order.dto.request.OrderChangeStatusRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderCreateRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderDeleteRequest;
import com.pepponechoi.project.domain.order.dto.response.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse create(OrderCreateRequest request);
    List<OrderResponse> findAllByUser(Long userId);
    Boolean changeStatus(OrderChangeStatusRequest request);
    Boolean delete(OrderDeleteRequest request);
}

package com.sparta.project1.domain.order.service;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.order.api.dto.OrderResponse;
import com.sparta.project1.domain.order.api.dto.OrderedProductResponse;
import com.sparta.project1.domain.order.domain.Orders;
import com.sparta.project1.domain.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderFindService {
    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getOrderInfo(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Orders> orders = ordersRepository.findByUserId(userId, pageable);

        Page<OrderResponse> orderResponses = orders
                .map(this::getOrderResponse);
        return PageResponse.of(orderResponses, orderResponses.getContent());
    }

    public OrderResponse getOrderResponse(Orders orders) {
        List<OrderedProductResponse> orderedProducts = orders.getProductOrders()
                .stream()
                .map(po -> new OrderedProductResponse(po.getProduct().getId(),
                        po.getProduct().getName(),
                        po.getQuantity(),
                        po.getProduct().getPrice(),
                        po.getPrice()))
                .toList();

        return new OrderResponse(orders.getId(),
                orderedProducts,
                orders.getTotalPrice(),
                orders.getStatus().name(),
                orders.getShippingAddress(),
                orders.getCreatedAt());
    }

    public Orders getById(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("order not found"));
    }
}

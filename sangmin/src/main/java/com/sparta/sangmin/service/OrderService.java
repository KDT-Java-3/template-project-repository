package com.sparta.sangmin.service;

import com.sparta.sangmin.controller.dto.OrderRequest;
import com.sparta.sangmin.controller.dto.OrderResponse;
import com.sparta.sangmin.domain.Order;
import com.sparta.sangmin.domain.Product;
import com.sparta.sangmin.domain.User;
import com.sparta.sangmin.repository.OrderRepository;
import com.sparta.sangmin.repository.ProductRepository;
import com.sparta.sangmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + request.userId()));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + request.productId()));

        // 재고 확인 및 감소
        product.decreaseStock(request.quantity());

        Order order = new Order(user, product, request.quantity(), request.shippingAddress());
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + id));
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + id));

        Order.OrderStatus newStatus;
        try {
            newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 주문 상태입니다: " + status);
        }

        order.updateStatus(newStatus);
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + id));

        // 주문 취소 시 재고 복원
        Product product = order.getProduct();
        product.increaseStock(order.getQuantity());

        order.cancel();
        return OrderResponse.from(order);
    }
}

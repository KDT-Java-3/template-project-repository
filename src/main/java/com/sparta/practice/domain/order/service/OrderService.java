package com.sparta.practice.domain.order.service;

import com.sparta.practice.common.exception.ResourceNotFoundException;
import com.sparta.practice.domain.order.dto.OrderCreateRequest;
import com.sparta.practice.domain.order.dto.OrderItemRequest;
import com.sparta.practice.domain.order.dto.OrderResponse;
import com.sparta.practice.domain.order.entity.Order;
import com.sparta.practice.domain.order.entity.OrderItem;
import com.sparta.practice.domain.order.entity.OrderStatus;
import com.sparta.practice.domain.order.repository.OrderRepository;
import com.sparta.practice.domain.product.entity.Product;
import com.sparta.practice.domain.product.repository.ProductRepository;
import com.sparta.practice.domain.user.entity.User;
import com.sparta.practice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public OrderResponse createOrder(OrderCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 먼저 주문 생성
        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.getShippingAddress())
                .totalPrice(totalPrice)
                .build();

        // 주문 아이템 생성 및 재고 감소
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("상품을 찾을 수 없습니다."));

            // 재고 확인 및 감소
            product.decreaseStock(itemRequest.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);
            totalPrice = totalPrice.add(orderItem.calculateTotalPrice());
        }

        // 주문 아이템 설정 및 총 금액 계산
        order.getOrderItems().addAll(orderItems);
        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.from(savedOrder);
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("주문을 찾을 수 없습니다."));
        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse completeOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("주문을 찾을 수 없습니다."));
        order.complete();
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("주문을 찾을 수 없습니다."));
        order.cancel();
        return OrderResponse.from(order);
    }
}

package com.sparta.heesue.service;

import com.sparta.heesue.dto.request.OrderRequestDto;
import com.sparta.heesue.dto.response.OrderResponseDto;
import com.sparta.heesue.entity.*;
import com.sparta.heesue.repository.OrderItemRepository;
import com.sparta.heesue.repository.OrderRepository;
import com.sparta.heesue.repository.ProductRepository;
import com.sparta.heesue.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 주문 생성
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 재고 확인
        if (product.getStockQuantity() < requestDto.getQuantity()) {
            throw new IllegalStateException("상품 재고가 부족합니다.");
        }

        // 재고 감소
        product.decreaseStock(requestDto.getQuantity());

        BigDecimal totalPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(requestDto.getQuantity()));

        Orders order = Orders.builder()
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(totalPrice)
                .shippingAddress(requestDto.getShippingAddress())
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(requestDto.getQuantity())
                .price(product.getPrice())
                .build();
        orderItemRepository.save(orderItem);

        return new OrderResponseDto(order);
    }

    // 특정 사용자의 주문 목록 조회
    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return orderRepository.findByUser(user).stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주문 상태 변경
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (order.getOrderStatus() == OrderStatus.PENDING &&
                (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED)) {
            order.updateStatus(newStatus);
        } else {
            throw new IllegalStateException("이 상태로 변경할 수 없습니다.");
        }

        return new OrderResponseDto(order);
    }

    // 주문 취소
    public OrderResponseDto cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("대기 중인 주문만 취소할 수 있습니다.");
        }

        // 재고 복원
        order.getOrderItems().forEach(item -> {
            Product product = item.getProduct();
            product.increaseStock(item.getQuantity());
        });

        order.updateStatus(OrderStatus.CANCELLED);
        return new OrderResponseDto(order);
    }
}

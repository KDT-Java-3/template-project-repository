package com.sparta.bootcamp.java_2_example.service;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.common.enums.OrderStatus;
import com.sparta.bootcamp.java_2_example.domain.order.entity.Order;
import com.sparta.bootcamp.java_2_example.domain.order.entity.OrderItem;
import com.sparta.bootcamp.java_2_example.domain.order.repository.OrderRepository;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.domain.user.repository.UserRepository;
import com.sparta.bootcamp.java_2_example.dto.request.OrderRequest;
import com.sparta.bootcamp.java_2_example.dto.response.OrderResponse;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        log.info("주문 생성 요청: 사용자 ID {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다. ID: " + request.getUserId()));

        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.getShippingAddress())
                .build();

        // 주문 아이템 추가 및 재고 감소
        for (OrderRequest.OrderItemRequest itemRequest : request.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() ->new CustomException(ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + itemRequest.getProductId()));

            // 재고 감소
            product.decreaseStock(product.getName(), itemRequest.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();

            order.addOrderItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        log.info("주문 생성 완료: 주문번호 {}", savedOrder.getOrderNumber());

        return OrderResponse.from(savedOrder);
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));
        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByUserAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus newStatus) {
        log.info("주문 상태 변경 요청: ID {}, 새 상태 {}", id, newStatus);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));

        order.changeStatus(newStatus);

        log.info("주문 상태 변경 완료: 주문번호 {}", order.getOrderNumber());
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long id) {
        log.info("주문 취소 요청: ID {}", id);

        Order order = orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));

        order.cancel();

        // 재고 복원
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.increaseStock(orderItem.getQuantity());
        }

        log.info("주문 취소 완료: 주문번호 {}", order.getOrderNumber());
        return OrderResponse.from(order);
    }
}

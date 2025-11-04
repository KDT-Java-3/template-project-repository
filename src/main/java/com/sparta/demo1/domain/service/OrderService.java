package com.sparta.demo1.domain.service;

import com.sparta.demo1.domain.dto.request.OrderCreateRequest;
import com.sparta.demo1.domain.dto.request.OrderStatusUpdateRequest;
import com.sparta.demo1.domain.dto.response.OrderResponse;
import com.sparta.demo1.domain.entity.Order;
import com.sparta.demo1.domain.entity.OrderStatus;
import com.sparta.demo1.domain.entity.Product;
import com.sparta.demo1.domain.entity.User;
import com.sparta.demo1.domain.repository.OrderRepository;
import com.sparta.demo1.domain.repository.ProductRepository;
import com.sparta.demo1.domain.repository.UserRepository;
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
  public OrderResponse createOrder(OrderCreateRequest request) {
    // 사용자 조회
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    // 상품 조회
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

    // 재고 확인
    if (product.getStock() < request.getQuantity()) {
      throw new IllegalStateException("재고가 부족합니다.");
    }

    // 재고 차감
    product.decreaseStock(request.getQuantity());

    // 주문 생성
    Order order = Order.builder()
        .user(user)
        .product(product)
        .quantity(request.getQuantity())
        .shippingAddress(request.getShippingAddress())
        .orderStatus(OrderStatus.PENDING)
        .build();

    Order savedOrder = orderRepository.save(order);
    return OrderResponse.from(savedOrder);
  }

  public OrderResponse getOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
    return OrderResponse.from(order);
  }

  public List<OrderResponse> getOrdersByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
    return orders.stream()
        .map(OrderResponse::from)
        .collect(Collectors.toList());
  }

  public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
    List<Order> orders = orderRepository.findByOrderStatus(status);
    return orders.stream()
        .map(OrderResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

    order.updateStatus(request.getStatus());
    return OrderResponse.from(order);
  }

  @Transactional
  public OrderResponse cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

    // pending 상태만 취소 가능
    if (order.getOrderStatus() != OrderStatus.PENDING) {
      throw new IllegalStateException("대기 중인 주문만 취소할 수 있습니다.");
    }

    // 재고 복구
    Product product = order.getProduct();
    product.increaseStock(order.getQuantity());

    // 주문 취소
    order.cancel();
    return OrderResponse.from(order);
  }
}
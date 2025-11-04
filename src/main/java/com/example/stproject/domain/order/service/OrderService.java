package com.example.stproject.domain.order.service;

import com.example.stproject.domain.order.dto.OrderCreateRequest;
import com.example.stproject.domain.order.dto.OrderResponse;
import com.example.stproject.domain.order.dto.OrderStatusUpdateRequest;
import com.example.stproject.domain.order.entity.Order;
import com.example.stproject.domain.order.mapper.OrderMapper;
import com.example.stproject.domain.order.repository.OrderRepository;
import com.example.stproject.domain.order.type.OrderStatus;
import com.example.stproject.domain.product.entity.Product;
import com.example.stproject.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    /** 주문 생성: 재고 확인 → 차감 → 주문 저장 */
    @Transactional
    public Long create(OrderCreateRequest req) {
        // 1) 상품 잠금 조회
        Product product = productRepository.findByIdForUpdate(req.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + req.getProductId()));

        // 2) 재고 확인
        if (product.getStock() < req.getQuantity()) {
            throw new IllegalStateException("재고가 부족합니다. 남은 재고=" + product.getStock());
        }

        // 3) 재고 차감
        product.setStock(product.getStock() - req.getQuantity());

        // 4) 주문 생성
        Order order = Order.builder()
                .userId(req.getUserId())
                .product(product)
                .quantity(req.getQuantity())
                .shippingAddress(req.getShippingAddress())
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);
        log.debug("주문 생성 완료: id={}", saved.getId());
        return saved.getId();
    }

    /** 특정 사용자 주문 목록 조회 (상태 필터 optional) */
    public List<OrderResponse> getUserOrders(Long userId, OrderStatus status) {
        List<Order> orders = (status == null)
                ? orderRepository.findByUserIdOrderByIdDesc(userId)
                : orderRepository.findByUserIdAndStatusOrderByIdDesc(userId, status);
        return orders.stream().map(orderMapper::toResponse).toList();
    }

    /** 주문 상태 변경: PENDING → COMPLETED/CANCELED 만 허용 */
    @Transactional
    public void updateStatus(OrderStatusUpdateRequest req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + req.getOrderId()));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 주문만 상태 변경이 가능합니다.");
        }

        if (req.getStatus() == OrderStatus.COMPLETED) {
            order.setStatus(OrderStatus.COMPLETED);
        } else if (req.getStatus() == OrderStatus.CANCELED) {
            // 취소 시 재고 복구
            Product product = productRepository.findByIdForUpdate(order.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + order.getProduct().getId()));
            product.setStock(product.getStock() + order.getQuantity());
            order.setStatus(OrderStatus.CANCELED);
        } else {
            throw new IllegalArgumentException("허용되지 않는 상태 변경입니다: " + req.getStatus());
        }
    }

    /** 주문 취소 전용: PENDING만 가능 */
    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + orderId));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 주문만 취소할 수 있습니다.");
        }
        Product product = productRepository.findByIdForUpdate(order.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + order.getProduct().getId()));
        product.setStock(product.getStock() + order.getQuantity());
        order.setStatus(OrderStatus.CANCELED);
    }
}

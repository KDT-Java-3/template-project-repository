package com.example.demo.domain.order.service;

import com.example.demo.domain.order.dto.request.OrderCreateRequest;
import com.example.demo.domain.order.dto.request.OrderSearchCondition;
import com.example.demo.domain.order.dto.response.OrderItemResponse;
import com.example.demo.domain.order.dto.response.OrderResponse;
import com.example.demo.domain.order.dto.response.OrderSummary;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderItem;
import com.example.demo.domain.order.entity.OrderStatus;
import com.example.demo.domain.order.repository.OrderItemRepository;
import com.example.demo.domain.order.repository.OrderQueryRepository;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.product.entity.Product;
import com.example.demo.domain.product.repository.ProductRepository;
import com.example.demo.global.exception.ServiceException;
import com.example.demo.global.exception.ServiceExceptionCode;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성 (재고 차감 및 트랜잭션 처리)
     */
    @Transactional
    public Long createOrder(OrderCreateRequest request) {
        // 주문 항목 검증
        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            throw new ServiceException(ServiceExceptionCode.EMPTY_ORDER_ITEMS);
        }

        // Order 생성
        Order order = Order.builder()
            .userId(request.getUserId())
            .shippingAddress(request.getShippingAddress())
            .build();

        // OrderItem 생성 및 재고 차감
        for (OrderCreateRequest.OrderItemRequest itemRequest : request.getOrderItems()) {
            // 비관적 락으로 상품 조회
            Product product = productRepository.findByIdForUpdate(itemRequest.getProductId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

            // 수량 검증
            if (itemRequest.getQuantity() <= 0) {
                throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_QUANTITY);
            }

            // 재고 차감
            product.decreaseStock(itemRequest.getQuantity());

            // OrderItem 생성
            OrderItem orderItem = OrderItem.builder()
                .productId(product.getId())
                .quantity(itemRequest.getQuantity())
                .unitPrice(product.getPrice())
                .build();

            // 양방향 관계 설정
            order.addOrderItem(orderItem);
        }

        // Order 저장 (cascade로 OrderItem도 함께 저장)
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    /**
     * 주문 목록 조회 (동적 검색 + 페이징)
     */
    public Page<OrderSummary> getOrders(OrderSearchCondition condition, Pageable pageable) {
        return orderQueryRepository.search(condition, pageable);
    }

    /**
     * 주문 상세 조회
     */
    public OrderResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_ORDER));

        // OrderItems 조회
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        // OrderItemResponse 변환
        List<OrderItemResponse> orderItemResponses = orderItems.stream()
            .map(item -> OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build())
            .collect(Collectors.toList());

        // 총 주문 금액 계산
        BigDecimal totalAmount = orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderResponse.builder()
            .id(order.getId())
            .userId(order.getUserId())
            .status(order.getStatus())
            .orderDate(order.getOrderDate())
            .shippingAddress(order.getShippingAddress())
            .orderItems(orderItemResponses)
            .totalAmount(totalAmount)
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .build();
    }

    /**
     * 주문 취소 (재고 복구 및 트랜잭션 처리)
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_ORDER));

        // 취소 가능 여부 확인
        if (!order.canCancel()) {
            throw new ServiceException(ServiceExceptionCode.CANNOT_CANCEL_ORDER);
        }

        // OrderItems 조회
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        // 재고 복구 (비관적 락 사용)
        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findByIdForUpdate(orderItem.getProductId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT));

            product.increaseStock(orderItem.getQuantity());
        }

        // 주문 상태 변경
        order.changeStatus(OrderStatus.CANCELED);
    }

    /**
     * 주문 완료 처리
     */
    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_ORDER));

        // 상태 검증
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ServiceException(ServiceExceptionCode.INVALID_ORDER_STATUS);
        }

        order.changeStatus(OrderStatus.COMPLETED);
    }
}

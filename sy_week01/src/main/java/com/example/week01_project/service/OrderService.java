package com.example.week01_project.service;


import com.example.week01_project.domain.orders.*;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.dto.orders.OrdersDtos.*;
import com.example.week01_project.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final OrderShippingRepository orderShippingRepo;

    @Transactional
    public Resp create(CreateReq req) {
        // 1) 상품 잠그고(stock 체크)
        Product p = productRepo.findByIdForUpdate(req.productId())
                .orElseThrow(() -> new EntityNotFoundException("product not found"));

        if (p.getStock() < req.quantity()) {
            throw new IllegalStateException("out of stock");
        }

        // 2) 주문/항목/배송 생성
        Orders order = Orders.builder()
                .userId(req.userId())
                .status(OrderStatus.pending)
                .totalAmount(BigDecimal.ZERO)
                .build();
        orderRepo.save(order);

        BigDecimal priceSnap = p.getPrice();
        BigDecimal subtotal = priceSnap.multiply(BigDecimal.valueOf(req.quantity()));

        OrderItem oi = OrderItem.builder()
                .orderId(order.getId())
                .productId(p.getId())
                .productName(p.getName())
                .productPrice(priceSnap)
                .quantity(req.quantity())
                .subtotalAmount(subtotal)
                .build();
        orderItemRepo.save(oi);

        OrderShipping shipping = OrderShipping.builder()
                .orderId(order.getId())
                .recipientName(req.shipping().recipientName())
                .phone(req.shipping().phone())
                .addressLine1(req.shipping().addressLine1())
                .addressLine2(req.shipping().addressLine2())
                .city(req.shipping().city())
                .state(req.shipping().state())
                .postalCode(req.shipping().postalCode())
                .country(req.shipping().country() == null ? "KR" : req.shipping().country())
                .build();
        orderShippingRepo.save(shipping);

        // 3) 총액 갱신 & 재고 감소
        order.setTotalAmount(subtotal);

        p.setStock(p.getStock() - req.quantity()); // 낙관적/비관적 락으로 보호됨

        return new Resp(order.getId(), order.getStatus().name());
    }

    @Transactional(readOnly = true)
    public List<Orders> listByUser(Long userId) {
        return orderRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Resp changeStatus(Long orderId, ChangeStatusReq req) {
        Orders order = orderRepo.findById(orderId).orElseThrow(() -> new EntityNotFoundException("order not found"));
        OrderStatus target = OrderStatus.valueOf(req.status());
        // 허용 전이: pending -> completed / canceled
        if (order.getStatus() != OrderStatus.pending) {
            throw new IllegalStateException("status change allowed only from pending");
        }
        if (target == OrderStatus.completed || target == OrderStatus.canceled) {
            order.setStatus(target);
        } else {
            throw new IllegalStateException("invalid target status");
        }
        return new Resp(order.getId(), order.getStatus().name());
    }

    @Transactional
    public Resp cancel(Long orderId, Long userId) {
        Orders order = orderRepo.findById(orderId).orElseThrow(() -> new EntityNotFoundException("order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new IllegalStateException("user mismatch");
        }
        if (order.getStatus() != OrderStatus.pending) {
            throw new IllegalStateException("only pending can be canceled");
        }
        // 재고 복원
        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);
        for (OrderItem oi : items) {
            Product p = productRepo.findByIdForUpdate(oi.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("product not found"));
            p.setStock(p.getStock() + oi.getQuantity());
        }
        order.setStatus(OrderStatus.canceled);
        return new Resp(order.getId(), order.getStatus().name());
    }
}

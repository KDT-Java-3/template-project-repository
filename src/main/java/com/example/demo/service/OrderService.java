package com.example.demo.service;

import com.example.demo.controller.dto.OrderRequest;
import com.example.demo.controller.dto.OrderResponse;
import com.example.demo.controller.dto.OrderStatusRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        BigDecimal totalPrice = BigDecimal.ZERO;
        Purchase purchase = Purchase.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .shippingAddress(request.getShippingAddress())
                .status(Purchase.PurchaseStatus.PENDING)
                .build();

        // 상품 재고 확인 및 주문 아이템 생성
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + itemRequest.getProductId()));

            // 재고 확인 및 감소
            product.decreaseStock(itemRequest.getQuantity());

            BigDecimal itemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);

            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            purchase.addPurchaseItem(purchaseItem);
        }

        // 총 가격 설정
        purchase.updateTotalPrice(totalPrice);

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return toResponse(savedPurchase);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return purchaseRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrder(Long orderId) {
        Purchase purchase = purchaseRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        return toResponse(purchase);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatusRequest request) {
        Purchase purchase = purchaseRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Purchase.PurchaseStatus newStatus;
        try {
            newStatus = Purchase.PurchaseStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 주문 상태입니다: " + request.getStatus());
        }

        // 상태 변경 로직
        if (purchase.getStatus() == Purchase.PurchaseStatus.PENDING) {
            if (newStatus == Purchase.PurchaseStatus.COMPLETED || newStatus == Purchase.PurchaseStatus.CANCELLED) {
                purchase.updateStatus(newStatus);
                Purchase savedPurchase = purchaseRepository.save(purchase);
                return toResponse(savedPurchase);
            }
        }

        throw new IllegalArgumentException("주문 상태 변경이 불가능합니다: " + purchase.getStatus() + " → " + newStatus);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Purchase purchase = purchaseRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (purchase.getStatus() != Purchase.PurchaseStatus.PENDING) {
            throw new IllegalArgumentException("취소 가능한 주문은 PENDING 상태만 가능합니다.");
        }

        // 재고 복원
        for (PurchaseItem item : purchase.getPurchaseItems()) {
            item.getProduct().increaseStock(item.getQuantity());
        }

        purchase.updateStatus(Purchase.PurchaseStatus.CANCELLED);

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return toResponse(savedPurchase);
    }

    private OrderResponse toResponse(Purchase purchase) {
        return OrderResponse.builder()
                .id(purchase.getId())
                .user(OrderResponse.UserInfo.builder()
                        .id(purchase.getUser().getId())
                        .name(purchase.getUser().getName())
                        .email(purchase.getUser().getEmail())
                        .build())
                .totalPrice(purchase.getTotalPrice())
                .shippingAddress(purchase.getShippingAddress())
                .status(purchase.getStatus().name())
                .items(purchase.getPurchaseItems().stream()
                        .map(item -> OrderResponse.OrderItemResponse.builder()
                                .id(item.getId())
                                .product(OrderResponse.ProductInfo.builder()
                                        .id(item.getProduct().getId())
                                        .name(item.getProduct().getName())
                                        .price(item.getProduct().getPrice())
                                        .build())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .totalPrice(item.getTotalPrice())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .build();
    }
}


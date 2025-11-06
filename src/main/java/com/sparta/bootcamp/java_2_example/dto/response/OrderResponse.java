package com.sparta.bootcamp.java_2_example.dto.response;

import com.sparta.bootcamp.java_2_example.common.enums.OrderStatus;
import com.sparta.bootcamp.java_2_example.domain.order.entity.Order;
import com.sparta.bootcamp.java_2_example.domain.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String shippingAddress;
    private List<OrderItemResponse> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemResponse::from)
                        .collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;

        public static OrderItemResponse from(OrderItem orderItem) {
            return OrderItemResponse.builder()
                    .id(orderItem.getId())
                    .productId(orderItem.getProduct().getId())
                    .productName(orderItem.getProduct().getName())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .subtotal(orderItem.getSubtotal())
                    .build();
        }
    }
}

package com.example.stproject.domain.order.dto;

import com.example.stproject.domain.order.type.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String shippingAddress;
    private OrderStatus status;
    private LocalDateTime orderDate;
}
package com.sparta.demo1.domain.dto.response;

import com.sparta.demo1.domain.entity.Order;
import com.sparta.demo1.domain.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {

  private Long id;
  private Long userId;
  private String userName;
  private Long productId;
  private String productName;
  private Long productPrice;
  private Long quantity;
  private Long totalPrice;  // 상품 가격 * 수량
  private String shippingAddress;
  private OrderStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static OrderResponse from(Order order) {
    Long totalPrice = order.getProduct().getPrice() * order.getQuantity();

    return OrderResponse.builder()
        .id(order.getId())
        .userId(order.getUser() != null ? order.getUser().getId() : null)
        .userName(order.getUser() != null ? order.getUser().getUsername() : null)
        .productId(order.getProduct() != null ? order.getProduct().getId() : null)
        .productName(order.getProduct() != null ? order.getProduct().getName() : null)
        .productPrice(order.getProduct() != null ? order.getProduct().getPrice() : null)
        .quantity(order.getQuantity())
        .totalPrice(totalPrice)
        .shippingAddress(order.getShippingAddress())
        .status(order.getOrderStatus())
        .createdAt(order.getCreatedAt())
        .updatedAt(order.getUpdatedAt())
        .build();
  }
}

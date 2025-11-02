package com.sparta.demo.domain.order.dto.response;

import com.sparta.demo.domain.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NotNull
@Getter
public class OrderResponse {
    /** [FK] 유저 ID */
    private Long userId;
    /** 상품 정보 */
    private ProductResponse product;
    /** 수량 */
    private Integer quantity;
    /** 주문 총합 */
    private BigDecimal totalPrice;
    /** 주문 상태 */
    private Order.OrderStatus status;
    /** 주문 날짜 */
    private LocalDateTime orderDate;

    public static OrderResponse buildFromEntity(Order order) {
        return OrderResponse.builder()
                .userId(order.getUser().getId())
                .product(ProductResponse.buildFromEntity(order.getProduct()))
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getCreatedAt())
                .build();
    }
}

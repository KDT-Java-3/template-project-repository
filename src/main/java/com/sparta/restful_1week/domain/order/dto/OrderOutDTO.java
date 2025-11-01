package com.sparta.restful_1week.domain.order.dto;

import com.sparta.restful_1week.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutDTO {
    private Long id;
    private String userId;
    private String productId;
    private Integer quantity;
    private String shippingAddress;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public OrderOutDTO(Order order) {
        this.id = order.getId();
        this.userId =  order.getUserId();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.shippingAddress = order.getShippingAddress();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
}

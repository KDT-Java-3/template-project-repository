package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.OrderProduct;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderProductDto {
    private Long productId;
    private int quantity;
    private String name;
    private String description;

    public static OrderProductDto fromEntity(OrderProduct product) {
        return builder()
                .productId(product.getProduct().getId())
                .quantity(product.getQuantity())
                .description(product.getProduct().getDescription())
                .name(product.getProduct().getName())
                .build();
    }
}

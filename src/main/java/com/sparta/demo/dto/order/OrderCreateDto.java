package com.sparta.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer에서 사용하는 주문 생성 DTO
 */
@Getter
@AllArgsConstructor
public class OrderCreateDto {
    private Long userId;
    private String shippingAddress;
    private List<OrderItemDto> orderItems;

    public static OrderCreateDto from(OrderRequest request) {
        List<OrderItemDto> items = request.getOrderItems().stream()
                .map(item -> new OrderItemDto(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderCreateDto(
                request.getUserId(),
                request.getShippingAddress(),
                items
        );
    }

    @Getter
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long productId;
        private Integer quantity;
    }
}

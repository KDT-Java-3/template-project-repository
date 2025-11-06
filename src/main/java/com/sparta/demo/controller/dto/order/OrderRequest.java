package com.sparta.demo.controller.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "배송 주소는 필수입니다.")
    private String shippingAddress;

    @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다.")
    @Valid
    private List<OrderItemRequest> orderItems;

    public OrderRequest(Long userId, String shippingAddress, List<OrderItemRequest> orderItems) {
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
    }
}

package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "사용자 ID는 필수입니다")
    private Long userId;

    @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다")
    private List<OrderItemRequest> items;

    @NotBlank(message = "배송 주소는 필수입니다")
    @Size(max = 500, message = "배송 주소는 500자를 초과할 수 없습니다")
    private String shippingAddress;

    @Getter
    @Setter
    public static class OrderItemRequest {
        @NotNull(message = "상품 ID는 필수입니다")
        private Long productId;

        @NotNull(message = "수량은 필수입니다")
        private Integer quantity;
    }
}


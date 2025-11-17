package com.example.demo.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성 요청 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "배송 주소는 필수입니다.")
    private String shippingAddress;

    @NotEmpty(message = "주문 항목은 최소 1개 이상이어야 합니다.")
    private List<OrderItemRequest> orderItems;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {

        @NotNull(message = "상품 ID는 필수입니다.")
        private Long productId;

        @NotNull(message = "수량은 필수입니다.")
        @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
        private Integer quantity;
    }
}

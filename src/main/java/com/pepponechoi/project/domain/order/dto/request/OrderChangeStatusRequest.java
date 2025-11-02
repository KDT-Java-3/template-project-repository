package com.pepponechoi.project.domain.order.dto.request;

import com.pepponechoi.project.common.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderChangeStatusRequest {
    @NotBlank(message = "주문 ID는 필수입니다.")
    private Long orderId;
    @NotBlank(message = "유저 ID는 필수입니다.")
    private Long userId;
    @NotBlank(message = "배송지는 필수입니다.")
    private OrderStatus status;
}

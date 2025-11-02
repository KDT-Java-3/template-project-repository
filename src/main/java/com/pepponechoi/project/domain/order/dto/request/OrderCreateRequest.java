package com.pepponechoi.project.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @NotBlank(message = "제품 ID는 필수입니다.")
    private Long productId;
    @NotBlank(message = "유저 ID는 필수입니다.")
    private Long userId;
    @Positive(message = "주문량은 1이상이어야 합니다.")
    private Long quantity;
    @NotBlank(message = "배송지는 필수입니다.")
    private String shippingAddress;
}

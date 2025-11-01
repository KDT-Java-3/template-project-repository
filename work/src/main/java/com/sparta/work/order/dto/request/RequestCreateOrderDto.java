package com.sparta.work.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateOrderDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 0, message = "수량은 0보다 커야합니다.")
    private Integer quantity;

    @NotBlank
    private String shippingAddress;
}

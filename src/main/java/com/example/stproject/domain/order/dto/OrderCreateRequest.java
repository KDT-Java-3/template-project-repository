package com.example.stproject.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull @Min(1)
    private Integer quantity;

    @NotBlank
    private String shippingAddress;
}
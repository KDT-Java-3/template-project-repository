package com.sparta.bootcamp.work.domain.order.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String shippingAddress;

}

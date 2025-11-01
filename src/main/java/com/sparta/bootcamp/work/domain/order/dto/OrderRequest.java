package com.sparta.bootcamp.work.domain.order.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long productId;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private String shippingAddress;

}

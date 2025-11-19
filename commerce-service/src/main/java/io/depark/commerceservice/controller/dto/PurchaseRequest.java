package io.depark.commerceservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "배송 주소는 필수입니다.")
    private String shippingAddress;

    private List<PurchaseProduct> products;

    @Getter
    public static class PurchaseProduct {

        private Long productId;

        private Integer quantity;
    }
}


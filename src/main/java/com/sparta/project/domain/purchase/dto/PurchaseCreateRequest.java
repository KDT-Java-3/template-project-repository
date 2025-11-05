package com.sparta.project.domain.purchase.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PurchaseCreateRequest { //--수정 배송지shipping_address

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotEmpty(message = "주문할 상품 정보는 필수입니다.")
    private List<PurchaseItemRequest> items;

}

package com.jaehyuk.week_01_project.domain.purchase.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

/**
 * 주문 생성 요청 DTO
 *
 * @param shippingAddress 배송 주소 (필수)
 * @param items 주문 상품 목록 (필수, 최소 1개 이상)
 */
@Builder
public record CreatePurchaseRequest(
        @NotBlank(message = "배송 주소는 필수입니다")
        String shippingAddress,

        @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다")
        @Valid
        List<PurchaseItem> items
) {
    /**
     * 주문 상품 정보 (중첩 레코드)
     *
     * @param productId 상품 ID (필수)
     * @param quantity 수량 (필수, 1 이상)
     */
    @Builder
    public record PurchaseItem(
            @NotNull(message = "상품 ID는 필수입니다")
            Long productId,

            @NotNull(message = "수량은 필수입니다")
            @Min(value = 1, message = "수량은 1 이상이어야 합니다")
            Integer quantity
    ) {
    }
}

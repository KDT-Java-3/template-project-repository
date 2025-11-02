package com.sparta.ecommerce.purchase.application.dto;

import com.sparta.ecommerce.product.application.dto.ProductDto;
import com.sparta.ecommerce.product.domain.Product;
import com.sparta.ecommerce.purchase.domain.Purchase;
import com.sparta.ecommerce.purchase.domain.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PurchaseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseCreateRequest {
        @NotNull(message = "유저 일련번호가 필요합니다.")
        private Long userId;
        @NotNull(message = "제품 일련번호가 필요합니다.")
        private Long productId;
        @NotNull(message = "제품 개수가 필요합니다.")
        private Integer quantity;
        @NotNull(message = "배달 주소가 필요합니다.")
        private String shippingAddress;

        public Purchase toEntity(Product product) {
            return Purchase.builder()
                    .userId(this.userId)
                    .product(product)
                    .quantity(this.quantity)
                    .status(PurchaseStatus.PENDING)
                    .shippingAddress(this.shippingAddress)
                    .build();
        }
    }


    @Getter
    @NotNull
    @Builder
    @AllArgsConstructor
    public static class PurchaseResponse {
        private Long orderId;
        private Long userId;
        private PurchaseStatus status;
        private ProductDto.ProductResponse product;
        private Integer quantity;
        private String shippingAddress;
        private LocalDateTime createdAt;

        public static PurchaseResponse fromEntity(Purchase order) {
            return PurchaseResponse.builder()
                    .orderId(order.getId())
                    .userId(order.getUserId())
                    .status(order.getStatus())
                    .product(ProductDto.ProductResponse.fromEntity(order.getProduct()))
                    .quantity(order.getQuantity())
                    .shippingAddress(order.getShippingAddress())
                    .createdAt(order.getCreatedAt())
                    .build();
        }
    }
}

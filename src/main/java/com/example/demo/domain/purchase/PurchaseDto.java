package com.example.demo.domain.purchase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseDto {
    // ============================================
    // Request DTO
    // - userId, totalPrice, status, shippingAddress, purchaseProducts
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotEmpty
        private String shippingAddress;
        @NotEmpty
        private List<OrderItem> products; // 상품 여러개 정보

        // 상품 1개 정보
        @Getter
        @Setter
        public static class OrderItem {
            @NotNull
            private Long productId;
            @Positive
            private Integer quantity;
        }
    }

    // ============================================
    // Response DTO
    // - userId, totalPrice, status, shippingAddress, purchaseProducts
    // - id, createdAt, updatedAt
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long userId;
        private BigDecimal totalPrice;
        private String status;
        private String shippingAddress;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        // private List<ProductItem> productItems;


        /**
         * Purchase 엔티티를 Response DTO로 변환하는 정적 팩토리 메서드
         */
        public static Response from(Purchase purchase) {
            return Response.builder()
                    .id(purchase.getId())
                    .userId(purchase.getUserId().getId())
                    .totalPrice(purchase.getTotalPrice())
                    .status(purchase.getStatus())
                    .shippingAddress(purchase.getShippingAddress())
                    .createdAt(purchase.getCreatedAt())
                    .updatedAt(purchase.getUpdatedAt())
                    .build();
        }
    }

}

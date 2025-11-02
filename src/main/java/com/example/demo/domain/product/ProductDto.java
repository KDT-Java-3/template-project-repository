package com.example.demo.domain.product;

import com.example.demo.domain.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {
    // ============================================
    // Request DTO
    // - categoryId, name, description, price, stockQuantity
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private Long categoryId;

        @NotBlank(message = "Name is required")
        private String name;

        private String description;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        private BigDecimal price;

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity must be zero or positive")
        private Integer stockQuantity;
    }

    // ============================================
    // Response DTO
    // - categoryId, name, description, price, stockQuantity
    // - id, createdAt, updatedAt
    // ============================================
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long categoryId;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stockQuantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        /**
         * Category 엔티티를 Response DTO로 변환하는 정적 팩토리 메서드
         */
        public static Response from(Product product) {
            return Response.builder()
                    .id(product.getId())
                    .categoryId(product.getCategoryId().getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stockQuantity(product.getStockQuantity())
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .build();
        }
    }
}

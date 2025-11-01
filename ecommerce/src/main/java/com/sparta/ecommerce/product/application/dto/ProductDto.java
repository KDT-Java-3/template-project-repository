package com.sparta.ecommerce.product.application.dto;

import com.sparta.ecommerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse{
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private LocalDateTime createdAt;

        public static ProductResponse fromEntity(Product product){
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .createdAt(product.getCreatedAt())
                    .build();
        }
    }

}

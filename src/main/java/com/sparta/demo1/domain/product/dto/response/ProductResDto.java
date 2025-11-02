package com.sparta.demo1.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class ProductResDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductInfo{
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;

        @Builder
        public ProductInfo(Long id, String name, String description, BigDecimal price, Integer stock) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.stock = stock;
        }
    }
}

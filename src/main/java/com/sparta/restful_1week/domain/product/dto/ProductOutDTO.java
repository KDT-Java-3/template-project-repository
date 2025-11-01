package com.sparta.restful_1week.domain.product.dto;

import com.sparta.restful_1week.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public ProductOutDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.description = product.getDescription();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}

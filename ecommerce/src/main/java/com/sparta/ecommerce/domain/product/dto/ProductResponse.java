package com.sparta.ecommerce.domain.product.dto;

import com.sparta.ecommerce.domain.product.entity.Product;
import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long category_id;
    private String category_name;
    private String description;

    public static ProductResponse fromEntity(Product product) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category_id(product.getCategory().getId())
                .category_name(product.getCategory().getName())
                .description(product.getDescription())
                .build();
    }
}

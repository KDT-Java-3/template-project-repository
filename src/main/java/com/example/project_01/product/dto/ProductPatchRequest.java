package com.example.project_01.product.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPatchRequest {

    String name;
    String description;
    Integer price;
    Integer stock;
    Integer categoryId;

    public static ProductPatchRequest from(ProductGetRequest request) {
        return ProductPatchRequest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .categoryId(request.getCategoryId())
                .build();
    }
}

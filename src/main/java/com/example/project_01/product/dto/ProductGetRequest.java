package com.example.project_01.product.dto;

import com.example.project_01.entity.CategoryEntity;
import com.example.project_01.entity.ProductEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductGetRequest {

    String name;
    String description;
    String category;
    Integer price;
    Integer stock;
    Integer categoryId;


    public static ProductGetRequest from(ProductEntity entity) {
        if(entity == null) return null;

        if(entity.getCategory() == null) return null;

        return ProductGetRequest.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .categoryId(entity.getCategory().getSeq())
                .build();
    }
}

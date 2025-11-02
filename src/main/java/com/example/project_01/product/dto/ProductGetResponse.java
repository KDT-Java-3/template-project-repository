package com.example.project_01.product.dto;

import com.example.project_01.entity.ProductEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductGetResponse {


    public static ProductGetResponse from(ProductEntity entity) {
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

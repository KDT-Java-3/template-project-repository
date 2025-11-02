package com.example.project_01.product.dto;

import com.example.project_01.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {

    String name;
    Integer price;
    Integer stock;
    Integer categoryId;

    public ProductEntity of() {
        return ProductEntity.builder()
                .name(this.getName())
                .price(this.getPrice())
                .stock(this.getStock())
                .build();
    }
}

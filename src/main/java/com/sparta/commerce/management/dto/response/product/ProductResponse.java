package com.sparta.commerce.management.dto.response.product;

import com.sparta.commerce.management.entity.Category;
import com.sparta.commerce.management.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    UUID id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    Category category;

    public static ProductResponse getProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .build();
    }

    public static List<ProductResponse> getProductList(List<Product> productList) {
        return productList.stream()
                .map(ProductResponse::getProduct)
                .collect(Collectors.toList());
    }

}


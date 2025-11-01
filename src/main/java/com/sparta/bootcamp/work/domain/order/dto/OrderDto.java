package com.sparta.bootcamp.work.domain.order.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.bootcamp.work.common.enums.OrderStatus;
import com.sparta.bootcamp.work.domain.order.entity.Order;
import com.sparta.bootcamp.work.domain.order.entity.OrderProduct;
import com.sparta.bootcamp.work.domain.product.dto.ProductDto;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    OrderStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ProductDto> productList;


    public static OrderDto fromOrder (Order order) {
        return OrderDto.builder()
                .status(order.getStatus())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public static OrderDto fromOrderProducts (Order order, List<Product> productList) {
        return OrderDto.builder()
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .productList(ProductDto.fromProducts(productList))
                .build();
    }

}

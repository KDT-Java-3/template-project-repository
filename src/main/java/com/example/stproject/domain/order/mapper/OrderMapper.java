package com.example.stproject.domain.order.mapper;

import com.example.stproject.domain.order.dto.OrderResponse;
import com.example.stproject.domain.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderResponse toResponse(Order order);
}
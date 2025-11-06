package com.sparta.demo.controller.mapper;

import com.sparta.demo.controller.dto.order.OrderRequest;
import com.sparta.demo.controller.dto.order.OrderResponse;
import com.sparta.demo.service.dto.order.OrderCreateDto;
import com.sparta.demo.service.dto.order.OrderDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Order Controller Layer Mapper
 * Request → Service DTO, Service DTO → Response 변환 담당
 */
@Component
public class OrderControllerMapper {

    /**
     * OrderRequest를 OrderCreateDto로 변환
     */
    public OrderCreateDto toCreateDto(OrderRequest request) {
        var orderItems = request.getOrderItems().stream()
                .map(item -> new OrderCreateDto.OrderItemDto(
                        item.getProductId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderCreateDto(
                request.getUserId(),
                request.getShippingAddress(),
                orderItems
        );
    }

    /**
     * OrderDto를 OrderResponse로 변환
     */
    public OrderResponse toResponse(OrderDto dto) {
        return new OrderResponse(
                dto.getId(),
                dto.getUserId(),
                dto.getStatus(),
                dto.getShippingAddress(),
                dto.getTotalPrice(),
                dto.getOrderItems(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

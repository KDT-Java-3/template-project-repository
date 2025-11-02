package com.sparta.work.order.mapper;

import com.sparta.work.order.domain.Order;
import com.sparta.work.order.dto.request.RequestCreateOrderDto;
import com.sparta.work.order.dto.response.ResponseOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    ResponseOrderDto toResponseDto(Order order);
}

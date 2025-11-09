package com.sprata.sparta_ecommerce.controller.mapper;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchOrderDto;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // spring bean 으로 등록!
public interface OrderMapper {
    OrderServiceSearchDto toServiceSearchDto(Long userId, SearchOrderDto searchOrderDto);
}

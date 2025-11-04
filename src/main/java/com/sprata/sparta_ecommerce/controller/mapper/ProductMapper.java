package com.sprata.sparta_ecommerce.controller.mapper;

import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.service.dto.ProductServiceInputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // spring bean 으로 등록!
public interface ProductMapper {

    ProductServiceInputDto toService(ProductRequestDto req);
}

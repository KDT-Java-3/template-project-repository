package com.example.demo.controller.mapper;


import com.example.demo.controller.dto.ProductRequestDto;
import com.example.demo.service.dto.ProductServiceInputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // spring bean으로 등록
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // ProductRequestDto -> ProductServiceInputDto
    @Mapping(source="name", target="productName")
    ProductServiceInputDto toService(ProductRequestDto req);

}

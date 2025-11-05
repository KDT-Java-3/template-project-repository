package com.example.demo.mapper;

import com.example.demo.controller.dto.ProductRequestDto;
import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.entity.Product;
import com.example.demo.service.dto.ProductServiceInputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductServiceInputDto toService(ProductRequestDto req);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponseDto toResponse(Product product);

    List<ProductResponseDto> toResponseList(List<Product> products);
}


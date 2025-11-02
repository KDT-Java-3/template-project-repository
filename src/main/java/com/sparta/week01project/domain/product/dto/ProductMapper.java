package com.sparta.week01project.domain.product.dto;

import com.sparta.week01project.domain.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
}

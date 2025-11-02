package com.sparta.week01project.domain.product.controller.mapper;

import com.sparta.week01project.domain.product.controller.request.ProductCreateRequest;
import com.sparta.week01project.domain.product.controller.response.ProductResponse;
import com.sparta.week01project.domain.product.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {
    ProductDto toProductDto(ProductCreateRequest productCreateRequest);
    ProductResponse toProductResponse(ProductDto product);
}

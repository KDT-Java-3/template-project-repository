package com.sparta.work.product.mapper;

import com.sparta.work.category.mapper.CategoryMapper;
import com.sparta.work.product.domain.Product;
import com.sparta.work.product.dto.response.ResponseProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ResponseProductDto toResponseDto(Product product);
}

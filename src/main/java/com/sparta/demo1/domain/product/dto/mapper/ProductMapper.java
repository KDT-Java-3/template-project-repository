package com.sparta.demo1.domain.product.dto.mapper;

import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Ex)
     * @Mapping(source = "productName", target = "name") // productName -> name 이름이 다른 필드 매핑
     * @Mapping(source = "user.username", target = "username")  // 중첩 객체의 필드를 평탄화하여 매핑
     **/

    /**
     * ProductEntity -> ProductResDto.ProductInfo
     */
    ProductResDto.ProductInfo toRes(ProductEntity productEntity);
}

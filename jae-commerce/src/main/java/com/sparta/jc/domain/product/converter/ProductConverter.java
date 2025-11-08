package com.sparta.jc.domain.product.converter;

import com.sparta.jc.domain.product.converter.component.ProductConvertingHelper;
import com.sparta.jc.domain.product.handler.dto.ProductCreateRequest;
import com.sparta.jc.domain.product.handler.dto.ProductUpdateRequest;
import com.sparta.jc.domain.product.service.dto.ProductServiceInputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 상품 컨버터
 */
@Mapper(componentModel = "spring", uses = {ProductConvertingHelper.class})
public interface ProductConverter {

    /**
     * 상품 신규 생성 요청 > 상품 서비스 DTO 변환
     */
    @Mapping(source = "description", target = "description", qualifiedByName = "DescriptionConverter")
    ProductServiceInputDto toServiceInputDto(ProductCreateRequest productCreateRequest);

    /**
     * ProductUpdateRequest > ProductServiceInputDto
     */
    ProductServiceInputDto toServiceInputDto(ProductUpdateRequest productCreateRequest);

}

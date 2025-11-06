package com.sparta.demo.controller.mapper;

import com.sparta.demo.controller.dto.product.ProductRequest;
import com.sparta.demo.controller.dto.product.ProductResponse;
import com.sparta.demo.service.dto.product.ProductCreateDto;
import com.sparta.demo.service.dto.product.ProductDto;
import com.sparta.demo.service.dto.product.ProductUpdateDto;
import org.springframework.stereotype.Component;

/**
 * Product Controller Layer Mapper
 * Request → Service DTO, Service DTO → Response 변환 담당
 */
@Component
public class ProductControllerMapper {

    /**
     * ProductRequest를 ProductCreateDto로 변환
     */
    public ProductCreateDto toCreateDto(ProductRequest request) {
        return new ProductCreateDto(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }

    /**
     * ProductRequest를 ProductUpdateDto로 변환
     */
    public ProductUpdateDto toUpdateDto(ProductRequest request) {
        return new ProductUpdateDto(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }

    /**
     * ProductDto를 ProductResponse로 변환
     */
    public ProductResponse toResponse(ProductDto dto) {
        return new ProductResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getCategoryId(),
                dto.getCategoryName(),
                dto.getCategoryPath(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

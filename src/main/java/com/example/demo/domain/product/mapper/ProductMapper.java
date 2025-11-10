package com.example.demo.domain.product.mapper;

import com.example.demo.domain.product.dto.request.ProductCreateRequest;
import com.example.demo.domain.product.dto.response.ProductResponse;
import com.example.demo.domain.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Request DTO -> Entity
     */
    Product toEntity(ProductCreateRequest request);

    /**
     * Entity -> Response DTO
     */
    ProductResponse toResponse(Product product);
}

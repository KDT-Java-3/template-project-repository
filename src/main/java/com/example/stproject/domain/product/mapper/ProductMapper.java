package com.example.stproject.domain.product.mapper;

import com.example.stproject.domain.category.entity.Category;
import com.example.stproject.domain.category.repository.CategoryRepository;
import com.example.stproject.domain.product.dto.ProductResponse;
import com.example.stproject.domain.product.dto.ProductUpdateRequest;
import com.example.stproject.domain.product.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // 단건 변환
    @Mapping(target = "categoryId", source = "category.id")
    ProductResponse toResponse(Product product);

    // 리스트 변환
    @Mapping(target = "categoryId", source = "category.id")
    List<ProductResponse> toResponseList(List<Product> products);

    // 수정 변환
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductUpdateRequest dto,
                              @MappingTarget Product entity,
                              @Context CategoryRepository categoryRepository);

    @AfterMapping
    default void fillCategory(ProductUpdateRequest dto,
                              @MappingTarget Product entity,
                              @Context CategoryRepository categoryRepository) {
        if (dto.getCategoryId() != null) {
            entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. CategoryId=" + dto.getCategoryId())));
        }
    }
}

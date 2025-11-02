package com.sparta.demo1.domain.category.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.category.dto.response.CategoryResDto;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.category.repository.CategoryRepository;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long registerCategory(String name, String description, Long parentId) {
        CategoryEntity parentCategoryEntity = categoryRepository.findById(parentId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        return categoryRepository.save(CategoryEntity.builder()
                        .name(name)
                        .description(description)
                        .parent(parentCategoryEntity)
                .build()).getId();
    }

    @Transactional
    public void updateCategory(Long id, String name, String description) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!name.isBlank()){
            categoryEntity.updateName(name);
        }

        if(!description.isBlank()){
            categoryEntity.updateDescription(description);
        }
    }

    public List<CategoryResDto.CategoryInfo> getAllCategory() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllWithProducts();

        List<CategoryResDto.CategoryInfo>  categoryInfoList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {

            List<ProductResDto.ProductInfo>  productInfoList = new ArrayList<>();
            for (ProductEntity productEntity : categoryEntity.getProductList()) {
                productInfoList.add(ProductResDto.ProductInfo.builder()
                                .id(productEntity.getId())
                                .name(productEntity.getName())
                                .description(productEntity.getDescription())
                                .price(productEntity.getPrice())
                                .stock(productEntity.getStock())
                        .build());
            }

            categoryInfoList.add(CategoryResDto.CategoryInfo.builder()
                            .id(categoryEntity.getId())
                            .parentId(categoryEntity.getParent().getId())
                            .name(categoryEntity.getName())
                            .description(categoryEntity.getDescription())
                            .productInfoList(productInfoList)
                    .build());
        }
        return categoryInfoList;
    }
}

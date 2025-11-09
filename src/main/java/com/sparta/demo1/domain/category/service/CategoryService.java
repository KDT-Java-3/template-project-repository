package com.sparta.demo1.domain.category.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.category.dto.mapper.CategoryMapper;
import com.sparta.demo1.domain.category.dto.response.CategoryResDto;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.category.repository.CategoryQueryDsl;
import com.sparta.demo1.domain.category.repository.CategoryRepository;
import com.sparta.demo1.domain.product.dto.mapper.ProductMapper;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryQueryDsl categoryQueryDsl;

    private final CategoryMapper categoryMapper;

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
    public void updateCategory(Long id, String name, String description, Long parentId) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!name.isBlank()){
            categoryEntity.updateName(name);
        }

        if(!description.isBlank()){
            categoryEntity.updateDescription(description);
        }

        if(parentId != null){
            CategoryEntity parentCategoryEntity = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

            categoryEntity.updateParent(parentCategoryEntity);
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<CategoryResDto.CategorySimpleInfo> getCategoryTop10Sale(){
        List<CategoryEntity> categoryEntityList = categoryQueryDsl.findCategoryTop10Sale();
        return categoryEntityList.stream()
                .map(categoryMapper::toRes)
                .toList();
    }

    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryQueryDsl.findCategoryExistChildren(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!categoryEntity.getChildrenCategoryList().isEmpty()){
            throw new CustomException(ExceptionCode.ALREADY_EXIST, "하위 카테고리가 존재하여 삭제 할 수 없습니다.");
        }

        if(!categoryEntity.getProductList().isEmpty()){
            throw new CustomException(ExceptionCode.ALREADY_EXIST, "해당 카테고리에 연관된 상품이 존재하여 삭제 할 수 없습니다.");
        }

        categoryRepository.deleteById(id);
    }
}

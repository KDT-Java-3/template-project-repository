package com.sparta.demo1.domain.product.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.category.repository.CategoryRepository;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.repository.ProductRepository;
import com.sparta.demo1.domain.product.repository.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long registerProduct(String name, String description, BigDecimal price, Integer stock, Long categoryId){
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        return productRepository.save(ProductEntity.builder()
                        .name(name)
                        .description(description)
                        .price(price)
                        .stock(stock)
                        .category(categoryEntity)
                .build()).getId();
    }

    @Transactional
    public void updateProduct(Long id, String name, String description, BigDecimal price, Integer stock, Long categoryId){

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!name.isBlank()){
            productEntity.updateName(name);
        }

        if(!description.isBlank()){
            productEntity.updateDescription(description);
        }

        if(price != null){
            productEntity.updatePrice(price);
        }

        if(stock != null){
            productEntity.updateStock(stock);
        }

        if(categoryId != null){
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));
            productEntity.updateCategory(categoryEntity);
        }
    }

    @Transactional(readOnly = true)
    public ProductResDto.ProductInfo getProductInfoDetail(Long id){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        return ProductResDto.ProductInfo.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProductResDto.ProductInfo> getProductInfoList(
            List<Long> filterCategoryIdList,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String nameKeyWord
    ) {

        Specification<ProductEntity> spec = Specification.allOf(
                ProductSpecification.categoryIn(filterCategoryIdList),
                ProductSpecification.minPrice(minPrice),
                ProductSpecification.maxPrice(maxPrice),
                ProductSpecification.nameContains(nameKeyWord)
        );

        return productRepository.findAll(spec)
                .stream()
                .map(product -> ProductResDto.ProductInfo.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()
                )
                .toList();
    }

}

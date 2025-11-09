package com.sparta.demo1.domain.product.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.category.repository.CategoryRepository;
import com.sparta.demo1.domain.product.dto.mapper.ProductMapper;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.enums.ProductOrderBy;
import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import com.sparta.demo1.domain.product.repository.ProductQueryDsl;
import com.sparta.demo1.domain.product.repository.ProductRepository;
import com.sparta.demo1.domain.product.repository.ProductSpecification;
import com.sparta.demo1.domain.purchase.entity.PurchaseProductEntity;
import com.sparta.demo1.domain.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryDsl productQueryDsl;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    private final PurchaseService purchaseService;

    @Transactional
    public Long registerProduct(String name, String description, BigDecimal price, Integer stock, Long categoryId){
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        return productQueryDsl.registerProduct(name, description, price, stock, categoryEntity);
//        return productRepository.save(ProductEntity.builder()
//                        .name(name)
//                        .description(description)
//                        .price(price)
//                        .stock(stock)
//                        .category(categoryEntity)
//                .build()).getId();
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

        return productMapper.toRes(productEntity);
//        return ProductResDto.ProductInfo.builder()
//                .id(productEntity.getId())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .stock(productEntity.getStock())
//                .build();
    }

    @Transactional(readOnly = true)
    public Page<ProductResDto.ProductInfo> getProductInfoList(
            List<Long> filterCategoryIdList,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String nameKeyWord,
            ProductStockFilter stockFilter,
            Pageable pageable,
            List<ProductOrderBy> productOrderByList
    ) {
        // QueryDSL로 페이징 포함 검색
        Page<ProductEntity> productPage = productQueryDsl.findProductOfFilter(
                filterCategoryIdList,
                minPrice,
                maxPrice,
                nameKeyWord,
                stockFilter,
                pageable,
                productOrderByList
        );

        // Entity → DTO 매핑
        List<ProductResDto.ProductInfo> dtoList = productPage.getContent()
                .stream()
                .map(productMapper::toRes)
                .toList();

        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }

    @Transactional
    public void deleteProduct(Long id){
        List<PurchaseProductEntity> productEntityList = purchaseService.findPurchasesOfCompletedStateByProductId(id);
        if(!productEntityList.isEmpty()){
            throw new CustomException(ExceptionCode.ALREADY_EXIST, "주문 완료 상태가 있는 제품으로 삭제가 불가합니다.");
        }

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        productRepository.deleteById(id);
    }

}

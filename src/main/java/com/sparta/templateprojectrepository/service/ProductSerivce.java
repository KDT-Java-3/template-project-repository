package com.sparta.templateprojectrepository.service;

import com.sparta.templateprojectrepository.dto.request.ProductCreateRequestDto;
import com.sparta.templateprojectrepository.dto.request.ProductFindRequestDto;
import com.sparta.templateprojectrepository.entity.Category;
import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.repository.CategoryRepository;
import com.sparta.templateprojectrepository.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSerivce {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(ProductCreateRequestDto dto) {
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(()->new IllegalArgumentException("존재히지 않는 카테고리입니다."));

        Product product = Product.builder()
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .description(dto.getDescription())
                .category(category)
                .build();

        return productRepository.save(product);
    }

    public Product getProduct(ProductFindRequestDto dto) {
        Product product =  productRepository
                .findById(dto.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("조회된 데이터 정보가 없습니다."));

        return product;
    }

    public Product modifyProduct(ProductCreateRequestDto dto) {
        Product currentProduct =  productRepository
                .findByProductName(dto.getProductName());

        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        Product updateProduct = Product.builder()
                .id(currentProduct.getId())
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .category(category)
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        return productRepository.save(updateProduct);
    }


}

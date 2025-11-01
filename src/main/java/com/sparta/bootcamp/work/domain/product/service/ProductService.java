package com.sparta.bootcamp.work.domain.product.service;

import com.sparta.bootcamp.work.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.work.domain.product.dto.ProductCreateRequest;
import com.sparta.bootcamp.work.domain.product.dto.ProductDto;
import com.sparta.bootcamp.work.domain.product.dto.ProductEditRequest;
import com.sparta.bootcamp.work.domain.product.dto.ProductSearchRequest;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import com.sparta.bootcamp.work.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.bootcamp.work.domain.product.dto.ProductDto.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long createProduct(ProductCreateRequest productCreateRequest) {

        return productRepository.save(Product.builder()
                .name(productCreateRequest.getName())
                .stock(productCreateRequest.getStock())
                .price(BigDecimal.valueOf(productCreateRequest.getPrice()))
                .description(productCreateRequest.getDescription())
                .category(categoryRepository.findById(productCreateRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")))
                .build()).getId();
    }


    public ProductDto getProductOne(ProductSearchRequest productSearchRequest ) {
          Product product = productRepository.findTopByCategoryAndNameAndPriceBetween(categoryRepository.findById(productSearchRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")),
                  productSearchRequest.getName(),
                  productSearchRequest.getPriceLopper(),
                  productSearchRequest.getPriceUpper()
                  ).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.fromProduct(product);
    }


    public List<ProductDto> getProductAll() {
        List<Product> products = productRepository.findAll();
        return ProductDto.fromProducts(products);
    }

    public ProductDto editProduct(ProductEditRequest productEditRequest ) {

        Product product = productRepository.findById(productEditRequest.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productEditRequest.getName());
        product.setStock(productEditRequest.getStock());
        product.setDescription(productEditRequest.getDescription());
        product.setPrice(BigDecimal.valueOf(productEditRequest.getPrice()));
        product.setCategory(categoryRepository.findById(productEditRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));

        productRepository.save(product);

        return fromProduct(product);
    }

}

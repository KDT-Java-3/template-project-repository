package com.sparta.work.product.service;

import com.sparta.work.category.domain.Category;
import com.sparta.work.category.domain.CategoryRepository;
import com.sparta.work.product.domain.Product;
import com.sparta.work.product.domain.ProductRepository;
import com.sparta.work.product.dto.request.RequestCreateProductDto;
import com.sparta.work.product.dto.request.RequestUpdateProductDto;
import com.sparta.work.product.dto.response.ResponseProductDto;
import com.sparta.work.product.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ResponseProductDto> findAllProduct(){
        List<Product> products = productRepository.findAllWithCategory();

        return products.stream().map(productMapper::toResponseDto).toList();
    }

    public ResponseProductDto findProductById(Long id){
        Product product = productRepository.findWithCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return productMapper.toResponseDto(product);
    }

    public List<ResponseProductDto> findByCategoryId(Long categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream().map(productMapper::toResponseDto).toList();
    }

    public List<ResponseProductDto> findByNameContainingIgnoreCase(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        return products.stream().map(productMapper::toResponseDto).toList();
    }

    public Long createProduct(RequestCreateProductDto dto){
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();

        return productRepository.save(product).getId();
    }

    @Transactional
    public Long updateProduct(Long id, RequestUpdateProductDto dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.update(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock(), category);

        return product.getId();
    }

}

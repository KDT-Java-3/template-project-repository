package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.CategoryRepository;
import com.sparta.demo.repository.ProductRepository;
import com.sparta.demo.service.dto.product.ProductCreateDto;
import com.sparta.demo.service.dto.product.ProductDto;
import com.sparta.demo.service.dto.product.ProductUpdateDto;
import com.sparta.demo.service.mapper.ProductServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductServiceMapper mapper;

    @Transactional
    public ProductDto createProduct(ProductCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.CATEGORY_NOT_FOUND, "ID: " + dto.getCategoryId()));

        Product product = Product.create(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                category
        );

        Product savedProduct = productRepository.save(product);
        return mapper.toDto(savedProduct);
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.PRODUCT_NOT_FOUND, "ID: " + id));
        return mapper.toDto(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword) {
        return productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.PRODUCT_NOT_FOUND, "ID: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.CATEGORY_NOT_FOUND, "ID: " + dto.getCategoryId()));

        product.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                category
        );

        return mapper.toDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ServiceException(
                    ServiceExceptionCode.PRODUCT_NOT_FOUND, "ID: " + id);
        }
        productRepository.deleteById(id);
    }
}

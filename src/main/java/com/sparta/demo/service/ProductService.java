package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.dto.product.ProductCreateDto;
import com.sparta.demo.dto.product.ProductDto;
import com.sparta.demo.dto.product.ProductUpdateDto;
import com.sparta.demo.repository.CategoryRepository;
import com.sparta.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductDto createProduct(ProductCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + dto.getCategoryId()));

        Product product = Product.create(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                category
        );

        Product savedProduct = productRepository.save(product);
        return ProductDto.from(savedProduct);
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id));
        return ProductDto.from(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword) {
        return productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword).stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + dto.getCategoryId()));

        product.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                category
        );

        return ProductDto.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id);
        }
        productRepository.deleteById(id);
    }
}

package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductCreateRequest;
import com.example.demo.service.dto.ProductDto;
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

    /**
     * 상품 등록
     */
    @Transactional
    public ProductDto createProduct(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return ProductDto.fromEntity(savedProduct);
    }

    /**
     * 모든 상품 조회
     */
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 단일 상품 조회
     */
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));
        return ProductDto.fromEntity(product);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductDto updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));
        }

        product.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                category
        );

        return ProductDto.fromEntity(product);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * 카테고리별 상품 조회
     */
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 상품명 검색
     */
    public List<ProductDto> searchProductsByName(String keyword) {
        return productRepository.findByNameContaining(keyword).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 가격 범위 검색
     */
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 복합 검색 (카테고리 + 가격 범위 + 상품명)
     */
    public List<ProductDto> searchProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword) {
        return productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
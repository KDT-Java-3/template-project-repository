package com.sparta.heesue.service;

import com.sparta.heesue.dto.request.ProductCreateRequestDto;
import com.sparta.heesue.dto.request.ProductUpdateRequestDto;
import com.sparta.heesue.dto.response.ProductResponseDto;
import com.sparta.heesue.entity.Category;
import com.sparta.heesue.entity.Product;
import com.sparta.heesue.repository.CategoryRepository;
import com.sparta.heesue.repository.ProductRepository;
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

    // 상품 등록
    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto request) {
        // 1. 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        // 2. Product Entity 생성
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .build();

        // 3. DB 저장
        Product savedProduct = productRepository.save(product);

        // 4. Response DTO 변환
        return new ProductResponseDto(savedProduct);
    }

    // 단일 상품 조회
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        return new ProductResponseDto(product);
    }

    // 전체 상품 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 카테고리별 상품 조회
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 상품명으로 검색
    public List<ProductResponseDto> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 가격 범위로 조회
    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 카테고리 + 가격 범위
    public List<ProductResponseDto> getProductsByCategoryAndPrice(
            Long categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 상품 수정
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto request) {
        // 1. 기존 상품 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 2. 카테고리 변경 시 조회
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        }

        // 3. 상품 정보 업데이트 (Product Entity에 update 메서드 필요)
        product.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                category
        );

        // 4. Response DTO 변환 (save 불필요 - @Transactional의 더티 체킹)
        return new ProductResponseDto(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        productRepository.delete(product);
    }
}
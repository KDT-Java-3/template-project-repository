package com.sparta.jc.domain.product.service;

import com.sparta.jc.domain.category.entity.Category;
import com.sparta.jc.domain.category.repository.CategoryRepository;
import com.sparta.jc.domain.product.entity.Product;
import com.sparta.jc.domain.product.handler.dto.ProductCreateRequest;
import com.sparta.jc.domain.product.handler.dto.ProductResponse;
import com.sparta.jc.domain.product.repository.ProductRepository;
import com.sparta.jc.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 상품 관련 비즈니스 로직 구현체
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 상품 등록
     */
    public ProductResponse createProduct(ProductCreateRequest request) {
        // 1. 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("카테고리 데이터를 찾을 수 없습니다."));

        // 2. 상품 엔티티 생성
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        // 3. DB 저장 후 응답 변환
        return ProductResponse.from(productRepository.save(product));
    }

    /**
     * 단일 상품 조회
     */
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("상품 데이터 찾을 수 없습니다."));
        return ProductResponse.from(product);
    }

    /**
     * 상품 목록 조회 (검색/필터링 포함)\
     */
    public Page<ProductResponse> getProducts(String keyword, Long categoryId, Integer minPrice, Integer maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.unrestricted();

        // 상품명 키워드 검색
        if (keyword != null && !keyword.isEmpty())
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + keyword + "%"));

        // 카테고리 필터
        if (categoryId != null)
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));

        // 가격 범위 필터
        if (minPrice != null)
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));

        if (maxPrice != null)
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));

        // 검색 결과 반환
        return productRepository.findAll(spec, pageable).map(ProductResponse::from);
    }

//    /**
//     * 상품 수정
//     */
//    public ProductResponse updateProduct(Long id, ProductRequest request) {
//        // 1. 기존 상품 조회
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("상품 데이터 찾을 수 없습니다."));
//
//        // 2. 카테고리 조회
//        Category category = categoryRepository.findById(request.getCategoryId())
//                .orElseThrow(() -> new ResourceNotFoundException("카테고리 데이터를 찾을 수 없습니다."));
//
//        // 3. 수정 필드 반영
//        product.setName(request.getName());
//        product.setDescription(request.getDescription());
//        product.setPrice(request.getPrice());
//        product.setStock(request.getStock());
//        product.setCategory(category);
//
//        // 4. 저장 및 반환
//        return ProductResponse.from(productRepository.save(product));
//    }
}

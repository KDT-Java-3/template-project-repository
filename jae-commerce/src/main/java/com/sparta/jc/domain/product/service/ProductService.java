package com.sparta.jc.domain.product.service;

import com.sparta.jc.domain.category.entity.Category;
import com.sparta.jc.domain.category.repository.CategoryRepository;
import com.sparta.jc.domain.product.entity.Product;
import com.sparta.jc.domain.product.exception.ProductServiceErrorCode;
import com.sparta.jc.domain.product.exception.ProductServiceException;
import com.sparta.jc.domain.product.handler.dto.ProductResponse;
import com.sparta.jc.domain.product.repository.ProductRepository;
import com.sparta.jc.domain.product.service.dto.ProductServiceInputDto;
import com.sparta.jc.domain.product.service.validator.ProductServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 상품 관련 비즈니스 로직 구현체
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    // 상품 레파지토리
    private final ProductRepository productRepository;
    // 카테고리 레파지토리
    private final CategoryRepository categoryRepository;
    // 상품 서비스 유효성검사기
    private final ProductServiceValidator serviceValidator;

    /**
     * 상품 등록
     */
    public ProductResponse createProduct(ProductServiceInputDto productInputDto) {
        // 상품명 중복 여부 체크
        serviceValidator.validateDuplicateName(productInputDto.getName());

        // TODO 카테고리 에러 코드 구현하기.
        // 카테고리 조회
        Category category = categoryRepository.findById(productInputDto.getCategoryId()).orElseThrow();

        /* --------- 비즈 로직 --------- */

        // 2. 상품 엔티티 생성
        Product product = Product.builder()
                .name(productInputDto.getName())
                .description(productInputDto.getDescription())
                .price(productInputDto.getPrice())
                .stock(productInputDto.getStock())
                .category(category)
                .build();


        // 3. DB 저장 후 응답 변환
        // TODO QueryDSL로 변경하기.
        return ProductResponse.from(productRepository.save(product));
    }

    /**
     * 상품 수정 서비스
     */
    public ProductResponse updateProduct(Long id, ProductServiceInputDto inputDto) {
        // 1. 기존 상품 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceException(ProductServiceErrorCode.PRODUCT_NOT_FOUND));

        // TODO 카테고리 도메인 구현하기.
        // 2.카테고리 조회
        Category newCategory = categoryRepository.findById(inputDto.getCategoryId()).orElseThrow();

        product.updateDetails(
                newCategory,
                inputDto.getName(),
                inputDto.getDescription(),
                inputDto.getPrice(),
                inputDto.getStock()
        );

        // . DB 저장 후 응답 변환
        // TODO QueryDSL로 변경하기.
        return ProductResponse.from(productRepository.save(product));
    }

    /* -------------------------------------- 이하 조회 메서드 -------------------------------------- */

    /**
     * 단일 상품 조회
     * // TODO QueryDSL로 변경하기.
     */
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductServiceException(ProductServiceErrorCode.PRODUCT_NOT_FOUND));
        return ProductResponse.from(product);
    }

}

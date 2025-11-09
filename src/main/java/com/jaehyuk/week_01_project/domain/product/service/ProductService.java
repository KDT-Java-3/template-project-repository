package com.jaehyuk.week_01_project.domain.product.service;

import com.jaehyuk.week_01_project.domain.category.entity.Category;
import com.jaehyuk.week_01_project.domain.category.repository.CategoryRepository;
import com.jaehyuk.week_01_project.domain.product.dto.CreateProductRequest;
import com.jaehyuk.week_01_project.domain.product.dto.ProductResponse;
import com.jaehyuk.week_01_project.domain.product.dto.UpdateProductRequest;
import com.jaehyuk.week_01_project.domain.product.entity.Product;
import com.jaehyuk.week_01_project.domain.product.repository.ProductRepository;
import com.jaehyuk.week_01_project.domain.product.repository.ProductSpecification;
import com.jaehyuk.week_01_project.exception.custom.CategoryNotFoundException;
import com.jaehyuk.week_01_project.exception.custom.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 상품을 등록합니다.
     *
     * @param request 상품 등록 요청 (name, description, price, stock, categoryId)
     * @return 생성된 상품의 ID
     * @throws CategoryNotFoundException 카테고리를 찾을 수 없을 때
     */
    @Transactional
    public Long createProduct(CreateProductRequest request) {
        // 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> {
                    log.warn("카테고리를 찾을 수 없음 - categoryId: {}", request.categoryId());
                    return new CategoryNotFoundException(
                            "카테고리를 찾을 수 없습니다. ID: " + request.categoryId()
                    );
                });

        // 상품 생성 및 저장
        Product product = Product.builder()
                .category(category)
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();

        Product savedProduct = productRepository.save(product);

        log.info("상품 등록 완료 - productId: {}, name: {}, categoryId: {}, price: {}, stock: {}",
                savedProduct.getId(), request.name(), request.categoryId(), request.price(), request.stock());

        return savedProduct.getId();
    }

    /**
     * 단일 상품 상세 조회
     *
     * @param productId 상품 ID
     * @return 상품 상세 정보 (카테고리 정보 포함)
     * @throws ProductNotFoundException 상품을 찾을 수 없을 때
     */
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("상품을 찾을 수 없음 - productId: {}", productId);
                    return new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + productId);
                });

        log.debug("상품 조회 완료 - productId: {}, name: {}", productId, product.getName());

        return ProductResponse.from(product);
    }

    /**
     * 상품 목록 조회 (필터링 + 페이징)
     *
     * @param categoryId 카테고리 ID (선택)
     * @param minPrice 최소 가격 (선택)
     * @param maxPrice 최대 가격 (선택)
     * @param keyword 상품명 키워드 (선택)
     * @param pageable 페이징 정보
     * @return 상품 목록 (페이징)
     */
    public Page<ProductResponse> getProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                              String keyword, Pageable pageable) {
        // Specification 조합 (선택적 필터링)
        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategoryId(categoryId))
                .and(ProductSpecification.hasMinPrice(minPrice))
                .and(ProductSpecification.hasMaxPrice(maxPrice))
                .and(ProductSpecification.hasNameContaining(keyword));

        // 페이징 조회
        Page<Product> products = productRepository.findAll(spec, pageable);

        log.info("상품 목록 조회 완료 - categoryId: {}, minPrice: {}, maxPrice: {}, keyword: {}, page: {}, size: {}, total: {}",
                categoryId, minPrice, maxPrice, keyword, pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements());

        // ProductResponse로 변환
        return products.map(ProductResponse::from);
    }

    /**
     * 상품 정보를 수정합니다.
     *
     * @param productId 상품 ID
     * @param request 상품 수정 요청 (name, description, price, stock, categoryId)
     * @return 수정된 상품의 ID
     * @throws ProductNotFoundException 상품을 찾을 수 없을 때
     * @throws CategoryNotFoundException 카테고리를 찾을 수 없을 때
     */
    @Transactional
    public Long updateProduct(Long productId, UpdateProductRequest request) {
        // 상품 존재 여부 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("상품을 찾을 수 없음 - productId: {}", productId);
                    return new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + productId);
                });

        // 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> {
                    log.warn("카테고리를 찾을 수 없음 - categoryId: {}", request.categoryId());
                    return new CategoryNotFoundException(
                            "카테고리를 찾을 수 없습니다. ID: " + request.categoryId()
                    );
                });

        // 상품 정보 수정
        product.update(request.name(), request.description(), request.price(), request.stock(), category);

        Product updatedProduct = productRepository.save(product);

        log.info("상품 수정 완료 - productId: {}, name: {}, categoryId: {}, price: {}, stock: {}",
                updatedProduct.getId(), request.name(), request.categoryId(), request.price(), request.stock());

        return updatedProduct.getId();
    }
}

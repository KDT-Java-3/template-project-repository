package com.sparta.project.domain.product.service;

import com.sparta.project.domain.category.entity.Category;
import com.sparta.project.domain.category.repository.CategoryRepository;
import com.sparta.project.domain.product.dto.ProductCreateRequest;
import com.sparta.project.domain.product.dto.ProductResponse;
import com.sparta.project.domain.product.dto.ProductSearchCondition;
import com.sparta.project.domain.product.dto.ProductUpdateRequest;
import com.sparta.project.domain.product.entity.Product;
import com.sparta.project.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public ProductResponse createProduct(ProductCreateRequest request) {
        // 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));

        // 상품 생성
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        // 저장
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    /**
     * 모든 상품 조회
     */
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductResponse.fromList(products);
    }

    /**
     * 특정 상품 조회
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));
        return ProductResponse.from(product);
    }

    /**
     * 상품 검색 (복합 조건)
     */
    public List<ProductResponse> searchProducts(ProductSearchCondition condition) {
        List<Product> products = productRepository.searchProducts(
                condition.getCategoryId(),
                condition.getKeyword(),
                condition.getMinPrice(),
                condition.getMaxPrice()
        );
        return ProductResponse.fromList(products);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        // 기존 상품 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));

        // 카테고리 조회 (변경하는 경우)
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));
        }

        // 상품 정보 업데이트
        product.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                category
        );

        // @Transactional에 의해 자동으로 변경 감지 및 DB 업데이트
        return ProductResponse.from(product);
    }
}
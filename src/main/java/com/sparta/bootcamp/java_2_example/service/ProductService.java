package com.sparta.bootcamp.java_2_example.service;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;
import com.sparta.bootcamp.java_2_example.domain.category.repository.CategoryRepository;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.java_2_example.dto.request.ProductRequest;
import com.sparta.bootcamp.java_2_example.dto.response.ProductResponse;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("상품 등록 요청: {}", request.getName());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("상품 등록 완료: ID {}", savedProduct.getId());

        return ProductResponse.from(savedProduct);
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id));
        return ProductResponse.from(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(Long categoryId, BigDecimal minPrice,
                                                BigDecimal maxPrice, String keyword) {
        log.info("상품 검색 - 카테고리: {}, 최소가격: {}, 최대가격: {}, 키워드: {}",
                categoryId, minPrice, maxPrice, keyword);

        return productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("상품 수정 요청: ID {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + request.getCategoryId()));
        }

        product.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                category
        );

        log.info("상품 수정 완료: ID {}", id);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("상품 삭제 요청: ID {}", id);

        if (!productRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + id);
        }

        productRepository.deleteById(id);
        log.info("상품 삭제 완료: ID {}", id);
    }
}

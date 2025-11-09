package com.sparta.jc.domain.product.service.validator;

import com.sparta.jc.domain.category.repository.CategoryRepository;
import com.sparta.jc.domain.product.exception.ProductServiceErrorCode;
import com.sparta.jc.domain.product.exception.ProductServiceException;
import com.sparta.jc.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 상품_서비스_유효성검사기.
 */
@Component
@RequiredArgsConstructor
public class ProductServiceValidator {

    // 상품 레파지토리
    private final ProductRepository productRepository;
    // 카테고리 레파지토리
    private final CategoryRepository categoryRepository;

    /**
     * 검증) 상품 이름 중복
     */
    public void validateDuplicateName(String name) {
        boolean exists = productRepository.existsByName(name);
        if (exists) throw new ProductServiceException(ProductServiceErrorCode.PRODUCT_NAME_DUPLICATION);
    }
}

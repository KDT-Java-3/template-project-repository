package com.spartaecommerce.product.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.product.domain.Product;
import com.spartaecommerce.product.domain.ProductRepository;
import com.spartaecommerce.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Long register(Product product) {
        ProductJpaEntity productJpaEntity = ProductJpaEntity.from(product);
        return productJpaRepository.save(productJpaEntity).getId();
    }

    @Override
    public Product getProduct(Long productId) {
        ProductJpaEntity productJpaEntity = productJpaRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "Failed to get Product. productId: " + productId));
        return productJpaEntity.toDomain();
    }
}

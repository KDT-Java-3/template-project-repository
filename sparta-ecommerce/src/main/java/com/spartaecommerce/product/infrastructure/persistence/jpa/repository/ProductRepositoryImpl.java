package com.spartaecommerce.product.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.query.ProductSearchQuery;
import com.spartaecommerce.product.domain.repository.ProductRepository;
import com.spartaecommerce.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final EntityManager entityManager;

    @Override
    public Long save(Product product) {
        ProductJpaEntity productJpaEntity = ProductJpaEntity.from(product);
        return productJpaRepository.save(productJpaEntity).getId();
    }

    @Override
    public Product getById(Long productId) {
        ProductJpaEntity productJpaEntity = productJpaRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(
                ErrorCode.ENTITY_NOT_FOUND,
                "Failed to get Product. productId: " + productId
            ));

        return productJpaEntity.toDomain();
    }

    @Override
    public List<Product> findAllByIdIn(List<Long> productIds) {
        return productJpaRepository.findAllByIdIn(productIds).stream()
            .map(ProductJpaEntity::toDomain)
            .toList();
    }

    @Override
    public List<Product> search(ProductSearchQuery searchQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductJpaEntity> criteriaQuery = criteriaBuilder.createQuery(ProductJpaEntity.class);
        Root<ProductJpaEntity> root = criteriaQuery.from(ProductJpaEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchQuery.categoryId() != null) {
            Predicate categoryId = criteriaBuilder.equal(
                root.get("categoryId"),
                searchQuery.categoryId()
            );
            predicates.add(categoryId);
        }

        if (searchQuery.keyword() != null && !searchQuery.keyword().isEmpty()) {
            Predicate name = criteriaBuilder.like(
                root.get("name"),
                "%" + searchQuery.keyword() + "%"
            );
            predicates.add(name);
        }

        if (searchQuery.minPrice() != null && searchQuery.maxPrice() != null) {
            Predicate price = criteriaBuilder.between(
                root.get("price"),
                searchQuery.minPrice().amount(),
                searchQuery.maxPrice().amount()
            );
            predicates.add(price);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<ProductJpaEntity> productJpaEntities = entityManager.createQuery(criteriaQuery).getResultList();

        return productJpaEntities.stream()
            .map(ProductJpaEntity::toDomain)
            .toList();
    }
}

package com.spartaecommerce.category.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.category.domain.entity.Category;
import com.spartaecommerce.category.domain.repository.CategoryRepository;
import com.spartaecommerce.category.infrastructure.persistence.jpa.entity.CategoryJpaEntity;
import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Long save(Category category) {
        CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.from(category);
        return categoryJpaRepository.save(categoryJpaEntity).getId();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryJpaRepository.existsByName(name);
    }

    @Override
    public Category getById(Long categoryId) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
            .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "categoryId: " + categoryId));

        return categoryJpaEntity.toDomain();
    }
}

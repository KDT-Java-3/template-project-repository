package com.spartaecommerce.category.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.category.domain.entity.Category;
import com.spartaecommerce.category.domain.repository.CategoryRepository;
import com.spartaecommerce.category.infrastructure.persistence.jpa.entity.CategoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        return null;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.empty();
    }
}

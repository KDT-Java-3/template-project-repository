package com.spartaecommerce.category.domain.repository;

import com.spartaecommerce.category.domain.entity.Category;

import java.util.Optional;

public interface CategoryRepository {

    Long save(Category category);

    Category getById(Long categoryId);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}

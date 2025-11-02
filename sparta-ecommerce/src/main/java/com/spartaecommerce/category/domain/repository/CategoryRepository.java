package com.spartaecommerce.category.domain.repository;

import com.spartaecommerce.category.domain.entity.Category;

public interface CategoryRepository {

    Long save(Category category);

    boolean existsByName(String name);

    Category getById(Long categoryId);
}

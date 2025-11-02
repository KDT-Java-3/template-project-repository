package com.sparta.ecommerce.category.infrastructure;

import com.sparta.ecommerce.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}

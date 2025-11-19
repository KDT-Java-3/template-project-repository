package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Boolean existsByParentId(Long id);
}

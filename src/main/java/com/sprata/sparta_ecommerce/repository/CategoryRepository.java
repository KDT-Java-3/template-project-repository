package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    Optional<Category> findByName(String name);

    List<Category> findByParentCategoryIsNull();
}

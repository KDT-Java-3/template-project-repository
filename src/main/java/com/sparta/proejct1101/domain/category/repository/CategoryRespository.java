package com.sparta.proejct1101.domain.category.repository;

import com.sparta.proejct1101.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

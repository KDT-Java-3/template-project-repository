package com.study.shop.domain.repository;

import com.study.shop.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c left join fetch c.products")
    List<Category> findAllWithProductsFetchJoin();

}

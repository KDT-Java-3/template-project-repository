package com.sparta.project1.domain.product.repository;

import com.sparta.project1.domain.category.domain.Category;
import com.sparta.project1.domain.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
                select p from Product p where (1 = 1) and
                ((
                    :name is not null
                    and p.name like concat('%', :name, '%')
                ) or (
                    :minPrice is not null and :maxPrice is null
                    and p.price >= :minPrice
                ) or (
                    :maxPrice is not null and :minPrice is null
                    and p.price <= :maxPrice
                ) or (
                    :minPrice is not null and :maxPrice is not null
                    and p.price >= :minPrice
                    and p.price <= :maxPrice
                ) or (
                    :category is not null
                    and p.category in :category
                ))
            """)
    Page<Product> findAllByKeywords(String name, Long minPrice, Long maxPrice, List<Category> category,
                                    Pageable pageable);
}

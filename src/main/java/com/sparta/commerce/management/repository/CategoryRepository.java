package com.sparta.commerce.management.repository;

import com.sparta.commerce.management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    //전체 목록 조회 이름순으로 정렬
    List<Category> findAllByOrderByNameAsc();

    //카테고리 찾기
    Category findCategoryByName(String name);

}

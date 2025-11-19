package io.depark.commerceservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.depark.commerceservice.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static io.depark.commerceservice.entity.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

//    public Category save(String name, String description, Category parent) {
//        jpaQueryFactory.insert(category)
//                .columns(category.name)
//    }
}

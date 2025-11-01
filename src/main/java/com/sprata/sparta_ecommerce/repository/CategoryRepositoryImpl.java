package com.sprata.sparta_ecommerce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.QCategory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sprata.sparta_ecommerce.entity.QCategory.*;

@RequiredArgsConstructor
public class CategoryRepositoryImpl  implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Category> findListPaging(PageDto pageDto) {
        return queryFactory.selectFrom(category)
                .offset(pageDto.getOffset())
                .limit(pageDto.getSize())
                .fetch();
    }
}

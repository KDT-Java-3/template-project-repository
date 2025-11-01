package com.sprata.sparta_ecommerce.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sprata.sparta_ecommerce.entity.QProduct.*;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchProducts(SearchProductDto searchDto, PageDto pageDto) {
        return queryFactory.selectFrom(product)
                .where(
                        isCategory(searchDto.getCategoryId()),
                        hasPriceRange(searchDto.getMinPrice(), searchDto.getMaxPrice()),
                        searchKeyword(searchDto.getKeyword())
                )
                .offset(pageDto.getOffset())
                .limit(pageDto.getSize())
                .fetch();
    }

    /** 카테고리 조회 조건 */
    private BooleanExpression isCategory(Long categoryId) {
        if(categoryId==null || categoryId==0L){
            return null;
        }else {
            return product.category.id.eq(categoryId);
        }
    }

    /** 검색어 설정 */
    private BooleanExpression searchKeyword(String keyword) {
        if(StringUtils.hasText(keyword)){
           return product.name.contains(keyword);
        }else {
           return null;
        }
    }
    /** 금액 설정 */
    private BooleanBuilder hasPriceRange(Long minPrice, Long maxPrice) {
        BooleanBuilder builder = new BooleanBuilder();
        if(minPrice!=null && maxPrice!=null){
            builder.and(product.price.between(minPrice, maxPrice));
        }else if(minPrice!=null){
            builder.and(product.price.goe(minPrice));
        }else if(maxPrice!=null){
            builder.and(product.price.loe(maxPrice));
        }

        return builder;
    }
}

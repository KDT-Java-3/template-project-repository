package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.repository.projection.CategorySalesProjection;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findListPaging(PageDto pageDto);

    List<CategorySalesProjection> findTop10SalesCategory();
}

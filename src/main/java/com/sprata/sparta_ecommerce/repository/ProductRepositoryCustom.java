package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchProducts(SearchProductDto searchDto, PageDto pageDto);

}

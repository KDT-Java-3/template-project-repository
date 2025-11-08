package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.projection.ProductCategoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchProducts(SearchProductDto searchDto, PageDto pageDto);
    Product findByProductId(Long id);
    List<Product> findWithCategory(Long id);
    Page<Product> findProductByPaging(Pageable pageable);
    List<ProductCategoryProjection> findProductWithCategory();

}

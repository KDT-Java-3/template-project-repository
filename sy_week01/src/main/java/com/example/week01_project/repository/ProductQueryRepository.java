package com.example.week01_project.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.week01_project.domain.product.Product;

import java.math.BigDecimal;

public interface ProductQueryRepository {
    Page<Product> search(Long categoryId,
                         BigDecimal minPrice,
                         BigDecimal maxPrice,
                         Boolean includeZeroStock,
                         Pageable pageable);
}

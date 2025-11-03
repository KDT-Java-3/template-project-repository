package com.sparta.proejct1101.domain.product.repository;

import com.sparta.proejct1101.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRespository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}

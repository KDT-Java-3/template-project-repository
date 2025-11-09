package com.sparta.demo1.domain.product.repository;

import com.sparta.demo1.domain.product.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id in :ids")
    List<ProductEntity> findAllByIdWithLock(@Param("ids") List<Long> ids);
}

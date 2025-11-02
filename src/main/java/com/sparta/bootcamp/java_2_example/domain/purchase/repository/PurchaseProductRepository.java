package com.sparta.bootcamp.java_2_example.domain.purchase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sparta.bootcamp.java_2_example.domain.purchase.entity.PurchaseProduct;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {

	@Query("SELECT p FROM PurchaseProduct p JOIN FETCH p.purchase pu JOIN FETCH pu.user u WHERE u.email = :email")
	Optional<List<PurchaseProduct>> findAllByUserEmail(@Param("email") String email);

}

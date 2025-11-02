package com.wodydtns.commerce.domain.refund.repository;

import com.wodydtns.commerce.domain.refund.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByMember_Id(Long id);

}

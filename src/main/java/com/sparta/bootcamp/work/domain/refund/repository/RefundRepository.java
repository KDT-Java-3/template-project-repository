package com.sparta.bootcamp.work.domain.refund.repository;

import com.sparta.bootcamp.work.domain.refund.entity.Refund;
import com.sparta.bootcamp.work.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByUser(User user);
}

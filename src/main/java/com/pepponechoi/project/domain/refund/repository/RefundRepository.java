package com.pepponechoi.project.domain.refund.repository;

import com.pepponechoi.project.domain.refund.entity.Refund;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository {
    List<Refund> findAllByUser_Id(Long id);
}

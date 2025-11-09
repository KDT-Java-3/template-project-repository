package com.example.week01_project.repository;

import com.example.week01_project.domain.refund.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RefundQueryRepository {
    Page<Refund> search(Long userId, Refund.Status status, Pageable pageable);
}

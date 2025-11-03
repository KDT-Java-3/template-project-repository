package com.example.demo.common.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 모든 엔티티에 공통으로 들어가는 생성/수정 시각 컬럼. - DB 컬럼명: created_at, updated_at (datetime(3)) - JPA
 * Auditing(@EnableJpaAuditing)으로 자동 세팅
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "datetime(3)")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime(3)")
    private LocalDateTime updatedAt;
}

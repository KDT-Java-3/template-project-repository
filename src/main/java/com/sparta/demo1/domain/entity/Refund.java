package com.sparta.demo1.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RefundStatus status = RefundStatus.PENDING;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Builder
  public Refund(User user, Order order, String reason) {
    this.user = user;
    this.order = order;
    this.reason = reason;
    this.status = RefundStatus.PENDING;
  }

  // 비즈니스 로직
  public void approve() {
    if (this.status != RefundStatus.PENDING) {
      throw new IllegalStateException("대기 중인 환불만 승인할 수 있습니다.");
    }
    this.status = RefundStatus.APPROVED;
  }

  public void reject() {
    if (this.status != RefundStatus.PENDING) {
      throw new IllegalStateException("대기 중인 환불만 거부할 수 있습니다.");
    }
    this.status = RefundStatus.REJECTED;
  }
}
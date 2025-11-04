package com.sparta.demo1.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @Setter
  private User user;

  @JoinColumn(name = "product_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @Setter
  private Product product;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private String shippingAddress;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus orderStatus;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column
  private LocalDateTime updatedAt;

  @Builder
  public Order(
      User user,
      Product product,
      Long quantity,
      String shippingAddress,
      OrderStatus orderStatus
  ) {
    this.user = user;
    this.product = product;
    this.quantity = quantity;
    this.shippingAddress = shippingAddress;
    this.orderStatus = orderStatus;
  }

  // 비즈니스 로직
  public void updateStatus(OrderStatus newStatus) {
    this.orderStatus = newStatus;
  }

  public void cancel() {
    if (this.orderStatus != OrderStatus.PENDING) {
      throw new IllegalStateException("대기 중인 주문만 취소할 수 있습니다.");
    }
    this.orderStatus = OrderStatus.CANCELED;
  }
}

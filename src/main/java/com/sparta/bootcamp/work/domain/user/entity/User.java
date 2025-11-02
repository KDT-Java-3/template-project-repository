package com.sparta.bootcamp.work.domain.user.entity;

import com.sparta.bootcamp.work.domain.order.entity.Order;
import com.sparta.bootcamp.work.domain.refund.entity.Refund;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

@Table(name = "users")
@Builder
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  String email;

  @Column(nullable = false)
  String passwordHash;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @Builder.Default
  List<Order> orders = new ArrayList<>();

  public void setName(String name) {
    if (StringUtils.hasText(name)) {
      this.name = name;
    }
  }

  public void setEmail(String email) {
    if (StringUtils.hasText(email)) {
      this.email = email;
    }
  }

  public void setPasswordHash(String passwordHash) {
    if (StringUtils.hasText(passwordHash)) {
      this.passwordHash = passwordHash;
    }
  }
}


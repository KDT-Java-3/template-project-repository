package com.sparta.bootcamp.java_2_example.domain.user.entity;

import com.sparta.bootcamp.java_2_example.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
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
  @CreatedDate
  LocalDateTime createdAt;

  @Column(nullable = false)
  @LastModifiedDate
  LocalDateTime updatedAt;

  @OneToMany(fetch = FetchType.EAGER)
  List<Order> purchases = new ArrayList<>();

  @Builder
  public User(
      String name,
      String email,
      String passwordHash,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

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

    public void update(
            String name,
            String email,
            String passwordHash) {
      if(name != null) {
          this.name = name;
      }
      if(email != null) {
          this.email = email;
      }
      if(passwordHash != null) {
          this.passwordHash = passwordHash;
      }
    }
}


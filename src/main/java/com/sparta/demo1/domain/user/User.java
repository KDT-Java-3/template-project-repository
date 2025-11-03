package com.sparta.demo1.domain.user;

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
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, length = 50)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @CreationTimestamp // 엔티티가 생성될 때의 시간이 자동으로 기록됩니다.
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp // 엔티티가 수정될 때의 시간이 자동으로 기록됩니다.
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Builder // 빌더 패턴으로 객체를 생성할 수 있게 합니다.
  public User(
      String username,
      String email,
      String passwordHash
  ) {
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
  }

}

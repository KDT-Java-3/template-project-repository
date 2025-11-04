package com.jaehyuk.week_01_project.domain.user.entity;

import com.jaehyuk.week_01_project.domain.purchase.entity.Purchase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "users")
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false, unique = true)  // unique 추가 권장
    String email;

    @Column(nullable = false)
    String passwordHash;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    // 해결 방법 1: LAZY로 변경 + mappedBy 추가
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Purchase> purchaseList = new ArrayList<>();

    @Builder
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
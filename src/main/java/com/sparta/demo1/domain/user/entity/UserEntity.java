package com.sparta.demo1.domain.user.entity;

import com.sparta.demo1.domain.user.enums.UserRate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// User.java
@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    UserRate userRate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @Builder
    public UserEntity(
            String name,
            String email,
            String passwordHash,
            UserRate userRate
    ) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRate = userRate;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateEmail(String newEmail) {
        this.name = newEmail;
    }

    public void updatePassword(String newPassword) {
        this.passwordHash = newPassword;
    }
}
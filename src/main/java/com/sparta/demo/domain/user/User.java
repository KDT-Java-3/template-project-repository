package com.sparta.demo.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(length = 255)
    private String address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드
    public static User create(String username, String email, String passwordHash) {
        return User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .build();
    }

    public static User createWithAddress(String username, String email, String passwordHash, String address) {
        return User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .address(address)
                .build();
    }

    // 업데이트 메서드
    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    public void updateProfile(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void updateAddress(String address) {
        this.address = address;
    }
}

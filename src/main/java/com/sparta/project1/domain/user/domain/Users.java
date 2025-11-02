package com.sparta.project1.domain.user.domain;

import com.sparta.project1.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;


    public static Users register(String name, String email, String rawPassword, PasswordEncoder passwordEncoder) {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(rawPassword, "rawPassword cannot be null");

        String passwordHash = passwordEncoder.encodePassword(rawPassword);

        String nickname = NicknameGenerator.generateNickname(); // 초기 닉네임 설정

        return new Users(name, nickname, email, UserRole.USER, passwordHash);
    }

    Users(String username, String nickname, String email, UserRole role, String passwordHash) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encodePassword(rawPassword);
    }
}

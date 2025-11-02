package com.sparta.project1.domain.user.domain;

public interface PasswordEncoder {
    String encodePassword(String rawPassword);

    boolean matchPassword(String rawPassword, String passwordHash);
}

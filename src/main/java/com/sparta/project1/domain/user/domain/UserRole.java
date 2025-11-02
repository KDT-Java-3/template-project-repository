package com.sparta.project1.domain.user.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum UserRole {
    ADMIN("admin"), MANAGER("manager"), USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public static String findUserRole(String userRole) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.role.equals(userRole))
                .findFirst()
                .map(r1 -> r1.role)
                .orElseThrow(NoSuchElementException::new);
    }
}

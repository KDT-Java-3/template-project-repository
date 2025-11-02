package com.example.stproject.domain.member.dto;

import com.example.stproject.domain.member.entity.User;

public class UserDto {
    private Long id;
    private String name;
    private String email;

    private UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // 정적 팩터리 메서드 — of 패턴
    /*
    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
    */

    // getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

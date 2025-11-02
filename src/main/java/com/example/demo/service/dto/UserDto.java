package com.example.demo.service.dto;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String email;

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}

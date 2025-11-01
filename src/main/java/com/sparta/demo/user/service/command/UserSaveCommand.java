package com.sparta.demo.user.service.command;

import com.sparta.demo.user.domain.User;

public record UserSaveCommand(
        String name
) {

    public User toEntity() {
        return User.create(name);
    }
}

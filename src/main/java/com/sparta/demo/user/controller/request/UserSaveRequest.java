package com.sparta.demo.user.controller.request;

import com.sparta.demo.user.service.command.UserSaveCommand;

public record UserSaveRequest(
        String name
) {

    public UserSaveCommand toCommand() {
        return new UserSaveCommand(name);
    }
}

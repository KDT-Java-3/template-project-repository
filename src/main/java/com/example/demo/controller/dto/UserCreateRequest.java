package com.example.demo.controller.dto;

import com.example.demo.service.dto.UserCreateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UserCreateRequest(
        @NotBlank @Size(max = 50) String username,
        @Email @NotBlank @Size(max = 100) String email,
        @NotBlank @Size(min = 6, max = 255) String password,
        @NotBlank @Size(min = 1, max = 20) String point
) {

    public UserCreateCommand toCommand() {
        return new UserCreateCommand(username.trim(), email.trim(), password, point);
    }
}


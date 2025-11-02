package com.sparta.project1.domain.user.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotNull
        @Size(max = 50)
        String username,

        @NotNull
        @Size(max = 255)
        String email,

        @NotNull
        @Size(max = 16)
        String password
) {
}

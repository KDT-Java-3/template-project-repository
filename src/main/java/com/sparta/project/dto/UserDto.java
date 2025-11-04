package com.sparta.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserCreateRequest {

        @NotBlank(message = "사용자 이름은 필수입니다.")
        @Size(max = 100, message = "사용자 이름은 100자를 초과할 수 없습니다.")
        private String name;

        @NotBlank(message = "사용자 이메일은 필수입니다.")
        @Size(max = 500, message = "이메일은 500자를 초과할 수 없습니다.")
        private String email;

        @NotBlank(message = "사용자 비밀번호는 필수입니다.")
        private String password;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {

        private Long id;
        private String name;
        private String email;
        private String password;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}

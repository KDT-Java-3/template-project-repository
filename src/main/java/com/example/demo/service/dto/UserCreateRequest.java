package com.example.demo.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// UserCreateRequest.java - 사용자 생성 요청
@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "사용자 이름은 필수입니다.")
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}

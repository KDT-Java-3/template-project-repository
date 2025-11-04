package com.jaehyuk.week_01_project.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청 DTO")
public record SignUpRequest(

        @Schema(description = "사용자 이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다")
        String name,

        @Schema(description = "이메일 주소", example = "hong@example.com")
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        String email,

        @Schema(description = "비밀번호 (영문, 숫자, 특수문자 포함 8-20자)", example = "Test1234!@")
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다"
        )
        String password
) {}
package com.sparta.demo1.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

  @Size(max = 50, message = "사용자 이름은 50자를 초과할 수 없습니다.")
  private String username;

  @Email(message = "올바른 이메일 형식이 아닙니다.")
  private String email;

  @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
  private String password;
}
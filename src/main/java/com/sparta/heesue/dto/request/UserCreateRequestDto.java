package com.sparta.heesue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
}

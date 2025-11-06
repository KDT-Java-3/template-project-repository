package com.sparta.demo.service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Service Layer User Create DTO
 * 사용자 생성 요청 데이터
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String username;
    private String email;
    private String password;
    private String address;
}

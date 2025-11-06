package com.example.demo.controller.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequestDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}

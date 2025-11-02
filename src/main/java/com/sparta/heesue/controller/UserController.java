package com.sparta.heesue.controller;

import com.sparta.heesue.dto.request.UserCreateRequestDto;
import com.sparta.heesue.dto.response.UserResponseDto;
import com.sparta.heesue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto request) {
        UserResponseDto response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    // 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }

    // 이메일로 조회
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}
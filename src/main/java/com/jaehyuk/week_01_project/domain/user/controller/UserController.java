package com.jaehyuk.week_01_project.domain.user.controller;

import com.jaehyuk.week_01_project.domain.user.dto.LoginRequest;
import com.jaehyuk.week_01_project.domain.user.dto.SignUpRequest;
import com.jaehyuk.week_01_project.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다")
    @PostMapping("/v1/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest request) {
        Long savedUserId = userService.signUp(request);
        return ResponseEntity.ok(savedUserId);
    }

    @Operation(summary = "로그인", description = "사용자를 로그인합니다")
    @PostMapping("/v1/login")
    public ResponseEntity<Long> login(@Valid @RequestBody LoginRequest request) {
        Long loginUserId = userService.login(request);
        return ResponseEntity.ok(loginUserId);
    }
}

package com.sparta.demo1.controller;

import com.sparta.demo1.domain.dto.request.UserCreateRequest;
import com.sparta.demo1.domain.dto.request.UserUpdateRequest;
import com.sparta.demo1.domain.dto.response.UserResponse;
import com.sparta.demo1.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
  @PostMapping
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
    UserResponse response = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
  @GetMapping("/{userId}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
    UserResponse response = userService.getUser(userId);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "전체 사용자 목록 조회", description = "모든 사용자를 조회합니다.")
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    List<UserResponse> responses = userService.getAllUsers();
    return ResponseEntity.ok(responses);
  }

  @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다.")
  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable Long userId,
      @Valid @RequestBody UserUpdateRequest request) {
    UserResponse response = userService.updateUser(userId, request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
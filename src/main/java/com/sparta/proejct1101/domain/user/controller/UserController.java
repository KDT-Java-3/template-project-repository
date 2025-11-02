package com.sparta.proejct1101.domain.user.controller;

import com.sparta.proejct1101.domain.user.dto.request.UserReq;
import com.sparta.proejct1101.domain.user.dto.response.UserRes;
import com.sparta.proejct1101.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // 기본 경로 설정
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserRes> createUser(@RequestBody UserReq request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserRes> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserRes>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // PUT /api/users/
    @PutMapping
    public ResponseEntity<UserRes> updateUser(@RequestBody UserReq request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

}

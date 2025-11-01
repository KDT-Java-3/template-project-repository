package com.sparta.bootcamp.java_2_example.controller;

import com.sparta.bootcamp.java_2_example.dto.request.UserRequest;
import com.sparta.bootcamp.java_2_example.dto.response.UserResponse;
import com.sparta.bootcamp.java_2_example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private  final UserService userService;

    /**
     * 사용자 등록
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 사용자 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse response = userService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 전체 유저리스트 조회
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUsers();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}

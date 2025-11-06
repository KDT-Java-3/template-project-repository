package com.example.demo.controller;

import com.example.demo.controller.dto.CreateUserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/hello")
    public String hello(@RequestBody CreateUserRequestDto request) {
        userService.save(
                User.builder()
                        .name(request.getUsername())
                        .email(request.getEmail())
                        .passwordHash(request.getPassword())
                        .build()
        );
        return "ok";
    }
}


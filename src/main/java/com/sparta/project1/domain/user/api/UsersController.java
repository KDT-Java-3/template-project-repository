package com.sparta.project1.domain.user.api;

import com.sparta.project1.domain.user.api.dto.UserRegisterRequest;
import com.sparta.project1.domain.user.service.UsersModifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersModifyService usersService;

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterRequest request) {
        usersService.register(request);

        return ResponseEntity.ok().build();
    }
}

package com.pepponechoi.project.domain.user.controller;

import com.pepponechoi.project.domain.user.dto.request.UserCreateRequest;
import com.pepponechoi.project.domain.user.dto.response.UserResponse;
import com.pepponechoi.project.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.ok().body(userResponse);
    }

    // 필터링은 나중에...
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ResponseEntity.ok().body(userResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok().body(userResponse);
    }
    // update나 delete는 나중에...
}

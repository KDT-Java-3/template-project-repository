package com.sparta.demo.user.controller;

import com.sparta.demo.user.controller.request.UserSaveRequest;
import com.sparta.demo.user.service.UserService;
import com.sparta.demo.user.service.command.UserSaveCommand;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> save(@RequestBody UserSaveRequest request) {
        UserSaveCommand command = request.toCommand();
        Long id = userService.save(command);
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }
}

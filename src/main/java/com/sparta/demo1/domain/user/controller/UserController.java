package com.sparta.demo1.domain.user.controller;

import com.sparta.demo1.common.model.ApiResponseModel;
import com.sparta.demo1.domain.user.dto.request.UserReqDto;
import com.sparta.demo1.domain.user.dto.response.UserResDto;
import com.sparta.demo1.domain.user.entity.UserEntity;
import com.sparta.demo1.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public ApiResponseModel<UserResDto.UserDetail> getUser(@PathVariable String email) {
        UserEntity user = userService.getUser(email);
        return new ApiResponseModel<>(UserResDto.UserDetail.builder()
                .user(user)
                .build());
    }

    @PostMapping("/create")
    public ApiResponseModel<String> createUser(@Valid @RequestBody UserReqDto.UserCreate userCreate) {
        return new ApiResponseModel<>(userService.createUser(userCreate.getName(), userCreate.getEmail(), userCreate.getPassword()));
    }

    @PostMapping("/update")
    public ApiResponseModel<Boolean> updateUser(@Valid @RequestBody UserReqDto.UserUpdate userUpdate) {
        userService.updateUser(userUpdate.getCurrentEmail(), userUpdate.getName(), userUpdate.getNewEmail(), userUpdate.getPassword());
        return new ApiResponseModel<>(true);
    }
}

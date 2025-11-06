package com.sparta.demo.controller;

import com.sparta.demo.common.ApiResponse;
import com.sparta.demo.controller.dto.user.UserRequest;
import com.sparta.demo.controller.dto.user.UserResponse;
import com.sparta.demo.controller.mapper.UserControllerMapper;
import com.sparta.demo.service.UserService;
import com.sparta.demo.service.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관리 Controller
 */
@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserControllerMapper mapper;

    /**
     * 사용자 등록
     */
    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        // Request → Service DTO 변환
        var createDto = mapper.toCreateDto(request);

        // Service 호출
        UserDto userDto = userService.createUser(createDto);

        // Service DTO → Response 변환
        UserResponse response = mapper.toResponse(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }
}

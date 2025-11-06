package com.sparta.demo.controller.mapper;

import com.sparta.demo.controller.dto.user.UserRequest;
import com.sparta.demo.controller.dto.user.UserResponse;
import com.sparta.demo.service.dto.user.UserCreateDto;
import com.sparta.demo.service.dto.user.UserDto;
import org.springframework.stereotype.Component;

/**
 * Controller Layer User Mapper
 * UserRequest와 UserResponse 간의 변환을 담당
 */
@Component
public class UserControllerMapper {

    /**
     * UserRequest를 UserCreateDto로 변환
     */
    public UserCreateDto toCreateDto(UserRequest request) {
        return new UserCreateDto(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress()
        );
    }

    /**
     * UserDto를 UserResponse로 변환
     */
    public UserResponse toResponse(UserDto dto) {
        return new UserResponse(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getStatus(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}

package com.sparta.demo.service.mapper;

import com.sparta.demo.domain.user.User;
import com.sparta.demo.service.dto.user.UserDto;
import org.springframework.stereotype.Component;

/**
 * Service Layer User Mapper
 * User Entity와 UserDto 간의 변환을 담당
 */
@Component
public class UserServiceMapper {

    /**
     * User Entity를 UserDto로 변환
     * 비밀번호는 포함하지 않음 (보안)
     */
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

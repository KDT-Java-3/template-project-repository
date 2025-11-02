package com.sparta.practice.domain.user.service;

import com.sparta.practice.domain.user.dto.UserCreateRequest;
import com.sparta.practice.domain.user.dto.UserResponse;
import com.sparta.practice.domain.user.entity.User;
import com.sparta.practice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPassword()) // 실제로는 암호화 필요
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }
}
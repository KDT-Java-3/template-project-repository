package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserCreateRequest;
import com.example.demo.service.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDto createUser(UserCreateRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        // User 엔티티 생성
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .build();

        // 저장
        User savedUser = userRepository.save(user);

        // DTO로 변환하여 반환
        return UserDto.fromEntity(savedUser);
    }

}

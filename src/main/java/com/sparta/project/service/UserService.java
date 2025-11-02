package com.sparta.project.service;

import com.sparta.project.dto.UserDto;
import com.sparta.project.entity.User;
import com.sparta.project.exception.DuplicateResourceException;
import com.sparta.project.exception.ResourceNotFoundException;
import com.sparta.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 등록
     */
    @Transactional
    public UserDto.UserResponse createUser(UserDto.UserCreateRequest request) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "이미 사용중인 이메일입니다: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .passwordHash(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    /**
     * 전체 사용자 조회
     */
    public List<UserDto.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 상세 조회
     */
    public UserDto.UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "사용자를 찾을 수 없습니다. ID: " + id));

        return mapToResponse(user);
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // ========== Mapper Methods ==========
    private UserDto.UserResponse mapToResponse(User user) {
        return UserDto.UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
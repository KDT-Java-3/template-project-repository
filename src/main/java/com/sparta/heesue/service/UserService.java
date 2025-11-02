package com.sparta.heesue.service;

import com.sparta.heesue.dto.request.UserCreateRequestDto;
import com.sparta.heesue.dto.response.UserResponseDto;
import com.sparta.heesue.entity.User;
import com.sparta.heesue.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 기본적으로 읽기 전용
public class UserService {
    private final UserRepository userRepository;

    // 회원 가입
    @Transactional  // 쓰기 작업은 따로 명시
    public UserResponseDto createUser(UserCreateRequestDto request) {
        // 1. 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. DTO → Entity 변환
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())  // 실제로는 암호화 필요!
                .phone(request.getPhone())
                .build();

        // 3. DB 저장
        User savedUser = userRepository.save(user);

        // 4. Entity → DTO 변환
        return new UserResponseDto(savedUser);
    }

    // 회원 조회
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return new UserResponseDto(user);
    }

    // 전체 회원 조회
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)  // User → UserResponseDto 변환
                .collect(Collectors.toList());
    }

    // 이메일로 조회
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return new UserResponseDto(user);
    }
}
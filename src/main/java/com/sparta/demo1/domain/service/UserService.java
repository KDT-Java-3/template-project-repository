package com.sparta.demo1.domain.service;

import com.sparta.demo1.domain.dto.request.UserCreateRequest;
import com.sparta.demo1.domain.dto.request.UserUpdateRequest;
import com.sparta.demo1.domain.dto.response.UserResponse;
import com.sparta.demo1.domain.entity.User;
import com.sparta.demo1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserResponse createUser(UserCreateRequest request) {
    // 이메일 중복 확인
    userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
      throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
    });

    // 비밀번호 해싱 (실제로는 BCryptPasswordEncoder 등을 사용해야 함)
    String passwordHash = hashPassword(request.getPassword());

    // 사용자 생성
    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .passwordHash(passwordHash)
        .build();

    User savedUser = userRepository.save(user);
    return UserResponse.from(savedUser);
  }

  public UserResponse getUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    return UserResponse.from(user);
  }

  public List<UserResponse> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(UserResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public UserResponse updateUser(Long userId, UserUpdateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    // 이메일 중복 확인 (본인 제외)
    if (request.getEmail() != null) {
      userRepository.findByEmail(request.getEmail()).ifPresent(existingUser -> {
        if (!existingUser.getId().equals(userId)) {
          throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
      });
    }

    // 비밀번호 해싱
    String passwordHash = null;
    if (request.getPassword() != null) {
      passwordHash = hashPassword(request.getPassword());
    }

    // 사용자 정보 업데이트
    user.updateUserInfo(request.getUsername(), request.getEmail(), passwordHash);

    return UserResponse.from(user);
  }

  @Transactional
  public void deleteUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    userRepository.delete(user);
  }

  // 실제로는 BCryptPasswordEncoder를 사용해야 합니다
  private String hashPassword(String password) {
    // 임시 구현 (실제로는 Spring Security의 BCryptPasswordEncoder 사용)
    return "hashed_" + password;
  }
}
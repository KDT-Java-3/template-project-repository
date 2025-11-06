package com.sparta.bootcamp.java_2_example.service;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.domain.user.repository.UserRepository;
import com.sparta.bootcamp.java_2_example.dto.request.UserRequest;
import com.sparta.bootcamp.java_2_example.dto.response.UserResponse;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(
                    ErrorCode.DUPLICATE_RESOURCE,
                    "이미 존재하는 유저입니다: " + request.getEmail()
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPassword()) //암호화 해야하지만... 사용자생성은 과제 범위라 생략..
                .build();
        User savedUser = userRepository.save(user);
        log.info("사용자 등록 완료 ID : {}", savedUser.getId());
        return UserResponse.from(savedUser);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "존재 하지 않는 사용자 입니다.")) ;
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

}

package com.sparta.demo.service;

import com.sparta.demo.domain.user.User;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.UserRepository;
import com.sparta.demo.service.dto.user.UserCreateDto;
import com.sparta.demo.service.dto.user.UserDto;
import com.sparta.demo.service.mapper.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관리 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceMapper mapper;

    /**
     * 사용자 등록
     * 1. 이메일 중복 확인 (비즈니스 규칙)
     * 2. 사용자명 중복 확인 (비즈니스 규칙)
     * 3. 비밀번호 암호화
     * 4. DTO를 Entity로 변환하여 DB에 저장
     * 5. Entity를 Response DTO로 변환하여 Controller에 반환
     */
    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        // 1. 이메일 중복 확인 (비즈니스 규칙)
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ServiceException(ServiceExceptionCode.DUPLICATE_EMAIL);
        }

        // 2. 사용자명 중복 확인 (비즈니스 규칙)
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ServiceException(ServiceExceptionCode.DUPLICATE_USERNAME);
        }

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 4. DTO를 Entity로 변환하여 DB에 저장
        User user;
        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            user = User.createWithAddress(
                    dto.getUsername(),
                    dto.getEmail(),
                    encodedPassword,
                    dto.getAddress()
            );
        } else {
            user = User.create(
                    dto.getUsername(),
                    dto.getEmail(),
                    encodedPassword
            );
        }
        User savedUser = userRepository.save(user);

        // 5. Entity를 Response DTO로 변환하여 Controller에 반환
        return mapper.toDto(savedUser);
    }
}

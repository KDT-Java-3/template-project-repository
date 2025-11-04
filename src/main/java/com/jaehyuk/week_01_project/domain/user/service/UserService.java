package com.jaehyuk.week_01_project.domain.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jaehyuk.week_01_project.domain.user.dto.LoginRequest;
import com.jaehyuk.week_01_project.domain.user.dto.SignUpRequest;
import com.jaehyuk.week_01_project.domain.user.entity.User;
import com.jaehyuk.week_01_project.domain.user.repository.UserRepository;
import com.jaehyuk.week_01_project.exception.custom.DuplicateEmailException;
import com.jaehyuk.week_01_project.exception.custom.InvalidPasswordException;
import com.jaehyuk.week_01_project.exception.custom.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.warn("중복된 사용자 회원가입 요청 - email: {}", request.email());
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다");
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, request.password().toCharArray());

        User saveUser = userRepository.save(
                User.builder()
                        .username(request.name())
                        .email(request.email())
                        .passwordHash(hashedPassword)
                        .build()
        );

        log.info("회원가입 완료 - userId: {}, email: {}", saveUser.getId(), request.email());

        return saveUser.getId();
    }

    public Long login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> {
                    log.warn("로그인 실패 - 존재하지 않는 이메일: {}", request.email());
                    return new UserNotFoundException("존재하지 않는 이메일입니다");
                });


        if (!BCrypt.verifyer().verify(request.password().toCharArray(), user.getPasswordHash()).verified) {
            log.warn("비밀번호 불일치 - email: {}", request.email());
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다");
        }

        log.info("로그인 성공 - userId: {}, email: {}", user.getId(), user.getEmail());

        return user.getId();
    }
}

package com.sparta.demo1.domain.user.service;

import com.sparta.demo1.common.error.CustomException;
import com.sparta.demo1.common.error.ExceptionCode;
import com.sparta.demo1.domain.user.entity.UserEntity;
import com.sparta.demo1.domain.user.enums.UserRate;
import com.sparta.demo1.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));
    }

    @Transactional
    public String createUser(String name, String email, String password) {
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                        .name(name)
                        .email(email)
                        .passwordHash(passwordEncoder.encode(password))
                        .userRate(UserRate.NORMAL)
                .build());
        return userEntity.getId().toString();
    }

    @Transactional
    public void updateUser(String currentEmail, String name, String newEmail, String password) {
        UserEntity user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST));

        if(!name.isBlank()){
            user.updateName(name);
        }

        if(!newEmail.isBlank()){
            Optional<UserEntity> userEntity = userRepository.findByEmail(newEmail);
            if(userEntity.isPresent()){
                throw new CustomException(ExceptionCode.ALREADY_EXIST);
            }
            user.updateEmail(newEmail);
        }

        if(!password.isBlank()){
           user.updatePassword(passwordEncoder.encode(password));
        }
    }
}

package com.sparta.project1.domain.user.service;

import com.sparta.project1.domain.user.api.dto.UserRegisterRequest;
import com.sparta.project1.domain.user.domain.PasswordEncoder;
import com.sparta.project1.domain.user.domain.Users;
import com.sparta.project1.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersModifyService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(UserRegisterRequest request) {
        Users user = Users.register(request.username(), request.email(), request.password(), passwordEncoder);

        usersRepository.save(user);
    }
}

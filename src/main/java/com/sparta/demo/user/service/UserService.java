package com.sparta.demo.user.service;

import com.sparta.demo.user.domain.User;
import com.sparta.demo.user.domain.UserRepository;
import com.sparta.demo.user.service.command.UserSaveCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(UserSaveCommand command) {
        User user = command.toEntity();
        userRepository.save(user);
        return user.getId();
    }
}

package com.sparta.commerce.domain.user.service;

import com.sparta.commerce.domain.user.repository.UserRepository;
import com.sparta.commerce.entity.User;
import com.sparta.commerce.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotFoundUserException::new);
    }

}

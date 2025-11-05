package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserJpaRepository;
import com.example.demo.service.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void save(User user) {
        userJpaRepository.save(user);
    }


}

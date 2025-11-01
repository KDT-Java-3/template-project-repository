package com.sparta.commerce.management.service;

import com.sparta.commerce.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}

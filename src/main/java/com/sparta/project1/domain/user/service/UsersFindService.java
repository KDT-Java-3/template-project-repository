package com.sparta.project1.domain.user.service;

import com.sparta.project1.domain.user.domain.Users;
import com.sparta.project1.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersFindService {
    private final UsersRepository usersRepository;

    public Users getById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("user not found"));
    }
}

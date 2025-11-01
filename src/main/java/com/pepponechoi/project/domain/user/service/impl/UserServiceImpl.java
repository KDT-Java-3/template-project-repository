package com.pepponechoi.project.domain.user.service.impl;

import com.pepponechoi.project.domain.user.dto.request.UserCreateRequest;
import com.pepponechoi.project.domain.user.dto.response.UserResponse;
import com.pepponechoi.project.domain.user.entity.User;
import com.pepponechoi.project.domain.user.repository.UserRepository;
import com.pepponechoi.project.domain.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            // 예외 던지기. 지금은 null
            return null;
        }

        User createUser = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(request.getPassword())
            .build();

        userRepository.save(createUser);

        return new UserResponse(createUser.getId(), createUser.getUsername(), createUser.getEmail());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map((User u) ->
                new UserResponse(u.getId(), u.getUsername(), u.getEmail()))
            .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            // 예외를 던져야 함.
            return null;
        }

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}

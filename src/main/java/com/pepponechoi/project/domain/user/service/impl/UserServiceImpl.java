package com.pepponechoi.project.domain.user.service.impl;

import com.pepponechoi.project.domain.user.dto.request.UserCreateRequest;
import com.pepponechoi.project.domain.user.dto.response.UserResponse;
import com.pepponechoi.project.domain.user.entity.User;
import com.pepponechoi.project.domain.user.repository.UserRepository;
import com.pepponechoi.project.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return null;
        }

        User createUser = new User(
            request.getEmail(),
            request.getUsername(),
            request.getPassword()
        );

        userRepository.save(createUser);

        return toUserResponse(createUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(this::toUserResponse)
            .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        if (id == null) {
            return null;
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;  // 나중에 예외로 변경
        }

        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
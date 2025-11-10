package com.example.demo.service;

import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.entity.User;
import com.example.demo.repository.UserJpaRepository;
import com.example.demo.service.dto.UserCreateCommand;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.dto.UserUpdateCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Transactional
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Transactional
    public UserDto createUser(UserCreateCommand command) {
        validateDuplicateEmail(command.email(), null);
        User user = User.builder()
                .name(command.username())
                .email(command.email())
                .passwordHash(command.password())
                .build();
        User saved = userJpaRepository.save(user);
        return UserDto.from(saved);
    }

    public List<UserDto> searchUser() {
        return userJpaRepository.findAll()
                .stream()
                .map(UserDto::from)
                .toList();
    }

    public UserDto getUserById(Long userId) {
        return UserDto.from(findUserById(userId));
    }

    @Transactional
    public UserDto updateUser(Long userId, UserUpdateCommand command) {
        User user = findUserById(userId);
        if (!user.getEmail().equals(command.email())) {
            validateDuplicateEmail(command.email(), userId);
        }
        user.updateProfile(command.username(), command.email());
        return UserDto.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userJpaRepository.delete(user);
    }

    private User findUserById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));
    }

    private void validateDuplicateEmail(String email, Long excludeUserId) {
        boolean exists = excludeUserId == null
                ? userJpaRepository.existsByEmail(email)
                : userJpaRepository.existsByEmailAndIdNot(email, excludeUserId);
        if (exists) {
            throw new ServiceException(ServiceExceptionCode.ALREADY_EXISTS_USER);
        }
    }
}

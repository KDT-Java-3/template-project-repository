package com.sparta.proejct1101.domain.user.service;

import com.sparta.proejct1101.domain.user.dto.request.UserReq;
import com.sparta.proejct1101.domain.user.dto.response.UserRes;
import com.sparta.proejct1101.domain.user.entity.User;
import com.sparta.proejct1101.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserRes createUser(UserReq request) {
        if (request.userId() == null || request.userId().isBlank()) {
            throw new IllegalArgumentException("userId 없음");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("password 없음");
        }

        User createUser = userRepository.save(
                User.builder()
                .userId(request.userId())
                .password(request.password())
                .userName(request.userName())
                .email(request.email())
                .build()
        );
        return UserRes.from(createUser);
    }

    @Override
    public UserRes getUser(Long id) {
        User user =  userRepository.findById(id).orElseThrow();
        return UserRes.from(user);
    }

    @Override
    public List<UserRes> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserRes::from).toList();
    }

    @Override
    @Transactional
    public UserRes updateUser(UserReq request) {
        User user = userRepository.findByUserId(request.userId());
        user.update(request.password(), request.userName(), request.email());
        return UserRes.from(user);
    }

}

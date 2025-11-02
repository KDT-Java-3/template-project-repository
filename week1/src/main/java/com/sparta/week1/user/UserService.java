package com.sparta.week1.user;

import com.sparta.week1.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        repository.save(user);
        return user;
    }
}

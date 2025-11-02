package com.pepponechoi.project.domain.user.service;

import com.pepponechoi.project.domain.user.dto.request.UserCreateRequest;
import com.pepponechoi.project.domain.user.dto.response.UserResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);

}

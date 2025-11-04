package com.sparta.proejct1101.domain.user.service;

import com.sparta.proejct1101.domain.user.dto.request.UserReq;
import com.sparta.proejct1101.domain.user.dto.response.UserRes;

import java.util.List;

public interface UserService {

    UserRes createUser(UserReq request);
    UserRes getUser(Long id);
    List<UserRes> getUsers();
    UserRes updateUser(UserReq request);
}

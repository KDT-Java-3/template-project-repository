package com.sparta.proejct1101.domain.user.dto.request;

public record UserReq(
        String userId,
        String password,
        String userName,
        String email
) {}

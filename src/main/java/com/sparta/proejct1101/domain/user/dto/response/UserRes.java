package com.sparta.proejct1101.domain.user.dto.response;

import com.sparta.proejct1101.domain.user.entity.User;
import java.time.LocalDateTime;

public record UserRes(
        String userId,
        String userName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UserRes from(User user) {
        return new UserRes(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

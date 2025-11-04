package com.example.demo.service.dto;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private int purchaseCount;
    // 다른 데이터
    private int a;
    private int b; // ....

    public static UserDto fromEntity(
            User user,
            int a,
            int b
    ) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .purchaseCount(user.getPurchase().size())
                .a(a)
                .b(b);
    }

}

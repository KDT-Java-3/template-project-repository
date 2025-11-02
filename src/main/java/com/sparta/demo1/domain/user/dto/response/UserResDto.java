package com.sparta.demo1.domain.user.dto.response;

import com.sparta.demo1.domain.user.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@NoArgsConstructor
public class UserResDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserDetail{
        private Long id;
        private String name;
        private String email;

        @Builder
        public UserDetail(UserEntity user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }
}

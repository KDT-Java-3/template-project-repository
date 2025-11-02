package com.sparta.demo1.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReqDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserCreate {

        @NotBlank
        @Size(min = 2, max = 30)
        private String name;

        @NotBlank
        @Email
        private String email;

        @Getter
        @Size(min = 2, max = 30)
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserUpdate {

        @NotBlank
        @Email
        private String currentEmail;

        @Size(min = 2, max = 30)
        private String name;

        @Email
        private String newEmail;

        @Getter
        @Size(min = 2, max = 30)
        private String password;
    }

}

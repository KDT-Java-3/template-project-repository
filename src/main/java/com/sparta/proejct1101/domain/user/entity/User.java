package com.sparta.proejct1101.domain.user.entity;

import com.sparta.proejct1101.domain.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String userName;
    private String email;


    public void update(String password, String userName, String email) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
        if (userName != null) {
            this.userName = userName;
        }
        if (email != null) {
            this.email = email;
        }
    }
}

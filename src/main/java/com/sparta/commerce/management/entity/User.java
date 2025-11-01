package com.sparta.commerce.management.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.Instant;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Size(max = 13)
    @NotNull
    @Column(name = "registration_number", nullable = false, length = 13)
    String registrationNumber;

    @Size(max = 8)
    @NotNull
    @Column(name = "birth", nullable = false, length = 8)
    String birth;

    @Size(max = 255)
    @Column(name = "email")
    String email;

    @Size(max = 11)
    @Column(name = "phone", length = 11)
    String phone;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_foreigner", nullable = false)
    Boolean isForeigner = false;

    @Size(max = 30)
    @NotNull
    @Column(name = "login_id", nullable = false, length = 30)
    String loginId;

    @NotNull
    @CreationTimestamp
    @Column(name = "rg_dt", nullable = false)
    Instant rgDt;

    @NotNull
    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    Instant udDt;

    @Builder
    public User(String name, String registrationNumber, String birth, String email, String phone, Boolean isForeigner, String loginId) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
        this.isForeigner = isForeigner;
        this.loginId = loginId;
    }
}

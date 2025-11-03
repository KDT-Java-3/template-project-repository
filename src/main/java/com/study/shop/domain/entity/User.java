package com.study.shop.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,  unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateDate;

}

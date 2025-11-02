package com.sparta.demo1.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String passwordHash;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @Builder
    public User(
            String name,
            String email,
            String passwordHash
    ) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

}
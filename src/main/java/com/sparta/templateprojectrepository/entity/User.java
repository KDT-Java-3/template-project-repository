package com.sparta.templateprojectrepository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "user_id")
    private Long Id;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="grade_id")
    private Grade grade;

    @Column(nullable = false,unique = true)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime modifiedAt;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy ="user", fetch = FetchType.LAZY)
    private List<Purchase> purchases = new ArrayList<>();

    @Builder
    public User(Long id, String passwordHash, String name, String email, String phoneNumber, String address, String zipCode, String grade) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
    }
}


package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;


}

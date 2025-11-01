package com.sparta.commerce.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    Category parent;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent")
    List<Category> children = new ArrayList<>();

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
    public Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
        //this.parent = parent;
    }

}

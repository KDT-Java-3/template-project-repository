package com.sparta.hodolee246.week01project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.hodolee246.week01project.service.request.CategoryDto;
import com.sparta.hodolee246.week01project.service.response.CategoryInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    public CategoryInfo toResponse() {
        return new CategoryInfo(
                this.id,
                this.name,
                this.description
        );
    }

    public void updateCategory(CategoryDto categoryDto) {
        this.name = categoryDto.name();
        this.description = categoryDto.description();
    }

    @Builder
    public Category(Long id, String name, String description, Category parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

}

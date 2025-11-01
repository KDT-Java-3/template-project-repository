package com.sparta.restful_1week.domain.category.entity;

import com.sparta.restful_1week.domain.category.dto.CategoryInDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "Category")
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_At", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_At", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Category(CategoryInDTO categoryInDTO) {
        this.name = categoryInDTO.getName();
        this.description = categoryInDTO.getDescription();
        this.createdAt = categoryInDTO.getCreatedAt();
        this.updatedAt = categoryInDTO.getUpdatedAt();
    }

    @Builder
    public void updateCategory(CategoryInDTO categoryInDTO) {
        this.name = categoryInDTO.getName();
        this.description = categoryInDTO.getDescription();
        this.updatedAt = categoryInDTO.getUpdatedAt();
    }

}

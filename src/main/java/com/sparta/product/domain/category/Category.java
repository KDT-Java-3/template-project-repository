package com.sparta.product.domain.category;

import com.sparta.product.domain.category.dto.request.UpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public void update(UpdateRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
    }
}

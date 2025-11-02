package com.sparta.product.domain.category.dto.request;

import com.sparta.product.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RegisterRequest {

    private String name;

    private String description;

    public Category toEntity() {
        return Category.builder()
                .name(this.getName())
                .description(this.getDescription())
                .build();
    }
}

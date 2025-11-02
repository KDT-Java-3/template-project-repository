package com.sparta.product.domain.category.dto.response;

import com.sparta.product.domain.category.Category;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RegisterResponse {

    private Long id;

    private String name;

    private String description;

    public static RegisterResponse of(Category category) {
        return RegisterResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}

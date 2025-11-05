package com.sparta.product.domain.category.dto.response;

import com.sparta.product.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateResponse {

    private Long id;

    private String name;

    private String description;

    public static UpdateResponse of(Category category) {
        return UpdateResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}

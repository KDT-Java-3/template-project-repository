package com.sparta.product.domain.category.dto.response;

import com.sparta.product.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResponse {

    private Long id;

    private String name;

    private String description;

    public static SearchResponse of(Category category) {
        return SearchResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}

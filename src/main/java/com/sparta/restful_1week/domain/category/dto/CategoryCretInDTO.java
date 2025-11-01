package com.sparta.restful_1week.domain.category.dto;

import com.sparta.restful_1week.domain.category.entity.Category;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCretInDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }

}


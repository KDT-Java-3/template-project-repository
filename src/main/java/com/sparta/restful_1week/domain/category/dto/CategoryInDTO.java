package com.sparta.restful_1week.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInDTO {

    private Long id;
    private String name;
    private String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}


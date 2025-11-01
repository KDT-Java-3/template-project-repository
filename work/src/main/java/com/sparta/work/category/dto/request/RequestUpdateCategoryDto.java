package com.sparta.work.category.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateCategoryDto {
    private String name;
    private String description;
}

package com.sparta.demo.domain.order.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateCategoryRequest {
    @Setter
    private Long id;
    @Size(max = 20)
    private String name;

    @Size(max = 255)
    private String description;
}

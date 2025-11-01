package com.sparta.commerce.management.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryUpdateRequest {
    UUID id;
    @NotNull(message = "카테고리명은 필수입니다")
    String name;
    String description;

}

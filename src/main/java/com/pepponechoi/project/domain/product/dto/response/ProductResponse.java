package com.pepponechoi.project.domain.product.dto.response;

import com.pepponechoi.project.domain.category.dto.response.CategoryResponse;
import com.pepponechoi.project.domain.user.dto.response.UserResponse;

public record ProductResponse(
    Long id,
    String name,
    String description,
    Long price,
    Long stock
) {

}

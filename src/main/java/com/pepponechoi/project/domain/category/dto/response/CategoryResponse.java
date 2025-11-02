package com.pepponechoi.project.domain.category.dto.response;

import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import java.util.List;

public record CategoryResponse(
    Long id,
    String name,
    String description,
    List<ProductResponse> products
) {

}

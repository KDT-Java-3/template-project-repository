package com.spartaecommerce.category.domain.commnad;

public record CategoryUpdateCommand(
    String name,
    String description
) {
}

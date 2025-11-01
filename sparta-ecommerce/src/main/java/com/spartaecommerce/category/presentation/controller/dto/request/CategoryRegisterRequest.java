package com.spartaecommerce.category.presentation.controller.dto.request;

import com.spartaecommerce.category.domain.commnad.CategoryRegisterCommand;

public record CategoryRegisterRequest(
    String name,
    String description
) {
    public CategoryRegisterCommand toCommand() {
        return new CategoryRegisterCommand(name, description);
    }
}

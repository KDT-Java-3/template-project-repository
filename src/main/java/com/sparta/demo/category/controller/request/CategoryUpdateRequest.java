package com.sparta.demo.category.controller.request;

import com.sparta.demo.category.service.command.CategoryUpdateCommand;

public record CategoryUpdateRequest(
        String name,
        String description
) {

    public CategoryUpdateCommand toCommand(Long categoryId) {
        return new CategoryUpdateCommand(categoryId, name, description);
    }
}

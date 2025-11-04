package com.sparta.demo.category.controller.request;

import com.sparta.demo.category.service.command.CategorySaveCommand;

public record CategorySaveRequest(
        String name,
        String description
) {

    public CategorySaveCommand toCommand() {
        return new CategorySaveCommand(name, description);
    }
}

package com.sparta.demo.category.service.command;

public record CategoryUpdateCommand(
        Long id,
        String name,
        String description
) {
}

package com.example.week01_project.dto.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDtos {
    public record CreateReq(@NotBlank String name, String description, Long parentId) {}
    public record UpdateReq(@NotBlank String name, String description, Long parentId) {}
    public record Resp(Long id, String name, String description, Long parentId) {}
}


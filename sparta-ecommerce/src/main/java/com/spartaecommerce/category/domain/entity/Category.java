package com.spartaecommerce.category.domain.entity;

import com.spartaecommerce.category.domain.commnad.CategoryRegisterCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    private Long id;

    private String name;

    private String description;

    private Long parentCategoryId;

    private List<Long> childrenCategoryIds = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Category createNew(CategoryRegisterCommand registerCommand) {
        return new Category(
            null,
            registerCommand.name(),
            registerCommand.description(),
            null,
            Collections.emptyList(),
            null,
            null
        );
    }
}

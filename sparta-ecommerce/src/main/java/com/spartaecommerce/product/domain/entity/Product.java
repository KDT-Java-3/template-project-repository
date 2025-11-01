package com.spartaecommerce.product.domain.entity;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.product.domain.command.ProductRegisterCommand;
import com.spartaecommerce.product.domain.command.ProductUpdateCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    private Long productId;

    private String name;

    private String description;

    private Money price;

    private Integer stock;

    private Long categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Product createNew(ProductRegisterCommand registerCommand) {
        return new Product(
            null,
            registerCommand.name(),
            registerCommand.description(),
            registerCommand.price(),
            registerCommand.stock(),
            registerCommand.categoryId(),
            null,
            null
        );
    }

    public void update(ProductUpdateCommand updateCommand) {
        if (updateCommand.name() != null && !updateCommand.name().isBlank()) {
            this.name = updateCommand.name();
        }

        if (updateCommand.description() != null && !updateCommand.description().isBlank()) {
            this.description = updateCommand.description();
        }

        if (updateCommand.price() != null) {
            this.price = updateCommand.price();
        }

        if (updateCommand.stock() != null) {
            this.stock = updateCommand.stock();
        }

        if (updateCommand.categoryId() != null) {
            this.categoryId = updateCommand.categoryId();
        }
    }
}

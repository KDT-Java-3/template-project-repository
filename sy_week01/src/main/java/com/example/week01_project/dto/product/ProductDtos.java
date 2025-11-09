package com.example.week01_project.dto.product;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductDtos {

    public record CreateReq(
            @NotBlank(message = "상품명은 필수입니다.") String name,
            String description,
            @NotNull @DecimalMin("0.00") BigDecimal price,
            @NotNull @Min(0) Integer stock,
            @NotNull Long categoryId
    ) {}

    public record UpdateReq(
            @NotBlank String name,
            String description,
            @NotNull @DecimalMin("0.00") BigDecimal price,
            @NotNull @Min(0) Integer stock,
            @NotNull Long categoryId
    ) {}

    public record Resp(
            Long id, String name, String description,
            BigDecimal price, Integer stock, Long categoryId, Boolean isActive
    ) {}
}

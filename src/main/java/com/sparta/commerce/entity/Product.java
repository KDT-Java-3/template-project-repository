package com.sparta.commerce.entity;

import com.sparta.commerce.domain.product.dto.ModifyProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@DynamicUpdate
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private BigDecimal price;

    private Integer stock;

    public void changeProduct(ModifyProductDto modifyProductDto) {
        this.name = modifyProductDto.name();
        this.description = modifyProductDto.description();
        this.price = modifyProductDto.price();
    }
}

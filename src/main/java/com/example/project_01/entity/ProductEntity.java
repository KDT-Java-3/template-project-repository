package com.example.project_01.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;

@Entity(name = "PRODUCT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false, updatable = false)
    Integer seq;

    @Column(name = "NAME", nullable = false, length = 30)
    String name;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "PRICE", nullable = false)
    @Check(constraints = "PRICE >= 0")
    Integer price;

    @Column(name = "STOCK", nullable = false)
    @Check(constraints = "STOCK >= 0")
    Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    CategoryEntity category;
}

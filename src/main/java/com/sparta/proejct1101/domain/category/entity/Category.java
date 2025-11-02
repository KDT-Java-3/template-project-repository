package com.sparta.proejct1101.domain.category.entity;

import com.sparta.proejct1101.domain.common.entity.BaseEntity;
import com.sparta.proejct1101.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    private String name;
    private String description;


    public void setParent(Category parent){
        this.parent = parent;
    }

    public void update(String name, String description) {
        if (this.name != null){
            this.name = name;
        }
        if (this.description != null){
            this.description = description;
        }
    }

}

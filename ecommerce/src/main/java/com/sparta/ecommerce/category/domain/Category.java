package com.sparta.ecommerce.category.domain;

import com.sparta.ecommerce.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("카테고리 일련번호")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Comment("카테고리명")
    private String name;

    @Column(name = "description", length = 2000)
    @Comment("카테고리 설명")
    private String description;

}

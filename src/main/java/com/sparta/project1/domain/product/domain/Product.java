package com.sparta.project1.domain.product.domain;


import com.sparta.project1.domain.BaseEntity;
import com.sparta.project1.domain.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ThreadUtils;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "mediumtext")
    private String description;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public static Product register(String name, Long price, String description, Integer stock, Category category) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(stock);
        Objects.requireNonNull(category);

        return new Product(name, BigDecimal.valueOf(price), description, stock, category);
    }

    Product(String name, BigDecimal price, String description, Integer stock, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }

    public void updateProduct(String name, Long price, String description, Integer stock, Category category) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.description = description;
        this.stock = stock;
        this.category = category;
    }

    public void checkStockRemaining(Integer stock) {
        if (this.stock < stock) {
            throw new OutOfStockException("out of stock, productId" + this.id);
        }
    }

    public void minusStock(Integer stock) {
        if (this.stock < stock) {
            throw new IllegalArgumentException("재고 개수 부족");
        }
        this.stock -= stock;
    }

    public void plusStock(Integer stock) {
        this.stock += stock;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

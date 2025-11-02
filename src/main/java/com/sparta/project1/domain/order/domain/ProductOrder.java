package com.sparta.project1.domain.order.domain;

import com.sparta.project1.domain.BaseEntity;
import com.sparta.project1.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    public static ProductOrder generate(Product product, Orders orders, Integer quantity) {
        // 구매한 product 개수(quantity) * product 의 가격
        BigDecimal price = product.getPrice().multiply(new BigDecimal(quantity));

        return new ProductOrder(product, orders, quantity, price);
    }

    ProductOrder(Product product, Orders orders, Integer quantity, BigDecimal price) {
        this.product = product;
        this.orders = orders;
        this.quantity = quantity;
        this.price = price;
    }
}

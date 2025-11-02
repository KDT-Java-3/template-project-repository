package com.sparta.ecommerce.domain.order.entity;

import com.sparta.ecommerce.domain.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Generated;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "order_id",
            nullable = false
    )
    private Order order;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;
    @Column(
            nullable = false
    )
    private int quantity;
    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    public OrderProduct(Order order, Product product, Integer quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public void connectOrder(Order order) {
        this.order = order;
    }

    @Generated
    public static OrderProductBuilder builder() {
        return new OrderProductBuilder();
    }

    @Generated
    public OrderProduct() {
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public Order getOrder() {
        return this.order;
    }

    @Generated
    public Product getProduct() {
        return this.product;
    }

    @Generated
    public int getQuantity() {
        return this.quantity;
    }

    @Generated
    public BigDecimal getPrice() {
        return this.price;
    }

    @Generated
    public static class OrderProductBuilder {
        @Generated
        private Order order;
        @Generated
        private Product product;
        @Generated
        private Integer quantity;
        @Generated
        private BigDecimal price;

        @Generated
        OrderProductBuilder() {
        }

        @Generated
        public OrderProductBuilder order(final Order order) {
            this.order = order;
            return this;
        }

        @Generated
        public OrderProductBuilder product(final Product product) {
            this.product = product;
            return this;
        }

        @Generated
        public OrderProductBuilder quantity(final Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        @Generated
        public OrderProductBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        @Generated
        public OrderProduct build() {
            return new OrderProduct(this.order, this.product, this.quantity, this.price);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.order);
            return "OrderProduct.OrderProductBuilder(order=" + var10000 + ", product=" + String.valueOf(this.product) + ", quantity=" + this.quantity + ", price=" + String.valueOf(this.price) + ")";
        }
    }
}

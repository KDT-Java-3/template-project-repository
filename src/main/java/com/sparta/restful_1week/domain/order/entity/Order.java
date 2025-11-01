package com.sparta.restful_1week.domain.order.entity;

import com.sparta.restful_1week.domain.category.entity.Category;
import com.sparta.restful_1week.domain.order.dto.OrderInDTO;
import com.sparta.restful_1week.domain.product.dto.ProductInDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Order")
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "created_At", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_At", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Order(OrderInDTO orderInDTO) {
        this.userId = orderInDTO.getUserId();
        this.productId = orderInDTO.getProductId();
        this.quantity = orderInDTO.getQuantity();
        this.shippingAddress = orderInDTO.getShippingAddress();
        this.createdAt = orderInDTO.getCreatedAt();
        this.updatedAt = orderInDTO.getUpdatedAt();
    }

    @Builder
    public void updateOrder(OrderInDTO orderInDTO) {
        this.userId = orderInDTO.getUserId();
        this.productId = orderInDTO.getProductId();
        this.quantity = orderInDTO.getQuantity();
        this.shippingAddress = orderInDTO.getShippingAddress();
        this.updatedAt = orderInDTO.getUpdatedAt();
    }
}

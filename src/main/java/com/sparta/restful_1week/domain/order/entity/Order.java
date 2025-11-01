package com.sparta.restful_1week.domain.order.entity;

import com.sparta.restful_1week.domain.order.dto.OrderInDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table@Entity
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

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
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

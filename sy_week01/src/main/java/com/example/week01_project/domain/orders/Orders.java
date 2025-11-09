package com.example.week01_project.domain.orders;

import com.example.week01_project.domain.product.Product;
import jakarta.persistence.*;
import jakarta.transaction.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Orders {
    @Enumerated(EnumType.STRING)
    private Status status;  // enum Status { PENDING, COMPLETED, CANCELED, REFUNDED }
    public enum Status { PENDING, COMPLETED, CANCELED, REFUNDED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="product_id")
    private Product product;

    @Min(1) private int quantity;

    @NotBlank
    private String shippingAddress;


    private LocalDateTime createdAt = LocalDateTime.now();

    // getter/setter
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public String getShippingAddress() { return shippingAddress; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setStatus(Status status) { this.status = status; }
}
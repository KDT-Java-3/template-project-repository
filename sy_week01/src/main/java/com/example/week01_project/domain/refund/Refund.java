package com.example.week01_project.domain.refund;


import com.example.week01_project.domain.orders.Orders;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="refunds")
public class Refund {
    public enum Status { PENDING, APPROVED, REJECTED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="order_id")
    private Orders orders;

    @NotBlank private String reason;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getter/setter
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Orders getOrder() { return orders; }
    public String getReason() { return reason; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setOrder(Orders order) { this.orders = order; }
    public void setReason(String reason) { this.reason = reason; }
    public void setStatus(Status status) { this.status = status; }
}
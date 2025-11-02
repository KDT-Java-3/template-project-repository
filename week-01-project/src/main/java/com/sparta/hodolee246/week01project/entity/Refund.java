package com.sparta.hodolee246.week01project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reason;

    @Enumerated(EnumType.STRING)
    @Comment("환불 상태")
    private RefundStatus status = RefundStatus.PENDING;

    @Column
    private LocalDateTime requestDate = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Refund(Long id, String reason, RefundStatus status, LocalDateTime requestDate, User user, Product product) {
        this.id = id;
        this.reason = reason;
        this.status = status;
        this.requestDate = requestDate;
        this.user = user;
        this.product = product;
    }
}

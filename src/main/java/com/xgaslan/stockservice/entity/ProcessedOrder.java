package com.xgaslan.stockservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderId;

    @Column(nullable = false)
    private LocalDateTime processedAt;
}

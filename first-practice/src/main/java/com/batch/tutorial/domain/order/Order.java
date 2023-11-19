package com.batch.tutorial.domain.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    private int price;
    private LocalDateTime orderDateTime;

    @Builder
    public Order(Long id, String orderName, int price, LocalDateTime orderDateTime) {
        this.id = id;
        this.orderName = orderName;
        this.price = price;
        this.orderDateTime = orderDateTime;
    }
}

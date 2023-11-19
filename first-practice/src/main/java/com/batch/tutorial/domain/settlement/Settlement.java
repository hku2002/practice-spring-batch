package com.batch.tutorial.domain.settlement;

import com.batch.tutorial.domain.order.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    private int price;
    private LocalDateTime orderDateTime;
    private LocalDateTime settlementDateTime;

    @Builder
    public Settlement(Long id, String orderName, int price, LocalDateTime orderDateTime, LocalDateTime settlementDateTime) {
        this.id = id;
        this.orderName = orderName;
        this.price = price;
        this.orderDateTime = orderDateTime;
        this.settlementDateTime = settlementDateTime;
    }

    public static Settlement from(Order order) {
        return Settlement.builder()
                .orderName(order.getOrderName())
                .price(order.getPrice())
                .orderDateTime(order.getOrderDateTime())
                .settlementDateTime(LocalDateTime.now())
                .build();
    }
}

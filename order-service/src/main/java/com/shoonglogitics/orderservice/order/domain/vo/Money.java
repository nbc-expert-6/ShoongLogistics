package com.shoonglogitics.orderservice.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Money {
    private Long amount;
}

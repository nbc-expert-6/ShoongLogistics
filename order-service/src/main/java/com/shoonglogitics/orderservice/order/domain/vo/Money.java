package com.shoonglogitics.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money {

    @Column(name = "total_price", nullable = false)
    private Long amount;

    public static Money of(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("결제금액은 0보다 작을 수 없습니다.");
        }
        Money money = new Money();
        money.amount = amount;
        return money;
    }
}

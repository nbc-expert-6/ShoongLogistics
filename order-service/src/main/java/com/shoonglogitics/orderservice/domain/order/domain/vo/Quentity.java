package com.shoonglogitics.orderservice.domain.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quentity {
    private Integer amount;

    public static Quentity of(Integer amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("주문 수량은 최소 1개 이상이어야 합니다.");
        }
        Quentity quentity = new Quentity();
        quentity.amount = amount;
        return quentity;
    }

}

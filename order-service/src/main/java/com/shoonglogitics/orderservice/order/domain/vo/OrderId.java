package com.shoonglogitics.orderservice.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderId {

    private UUID orderId;
}

package com.shoonglogitics.orderservice.domain.payment.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;

import lombok.Getter;

@Getter
public class PaymentCompletedEvent {
	private final UUID orderId;
	private final UUID productId;
	private final Integer quantity;
	private final LocalDateTime occurredAt;

	public PaymentCompletedEvent(Payment payment, UUID productId, Integer quantity) {
		this.orderId = payment.getOrderId();
		this.productId = productId;
		this.quantity = quantity;
		this.occurredAt = LocalDateTime.now();
	}
}

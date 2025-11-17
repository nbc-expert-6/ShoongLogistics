package com.shoonglogitics.orderservice.domain.payment.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;

public interface PaymentRepository {
	Payment save(Payment payment);

	Optional<Payment> findByOrderId(UUID orderId);
}

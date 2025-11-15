package com.shoonglogitics.orderservice.domain.payment.domain.repository;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;

public interface PaymentRepository {
	Payment save(Payment payment);
}

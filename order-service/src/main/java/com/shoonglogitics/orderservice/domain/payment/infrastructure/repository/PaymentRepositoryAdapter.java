package com.shoonglogitics.orderservice.domain.payment.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;
import com.shoonglogitics.orderservice.domain.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "Payment Repository Adapter")
public class PaymentRepositoryAdapter implements PaymentRepository {
	private final JpaPaymentRepository jpaPaymentRepository;

	@Override
	public Payment save(Payment payment) {
		jpaPaymentRepository.save(payment);
		return payment;
	}

	@Override
	public Optional<Payment> findByOrderId(UUID orderId) {
		return jpaPaymentRepository.findByOrderId(orderId);
	}
}

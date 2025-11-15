package com.shoonglogitics.orderservice.domain.payment.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;

public interface JpaPaymentRepository extends JpaRepository<Payment, UUID> {
}

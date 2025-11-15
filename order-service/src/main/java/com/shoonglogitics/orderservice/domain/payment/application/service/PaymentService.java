package com.shoonglogitics.orderservice.domain.payment.application.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;
import com.shoonglogitics.orderservice.domain.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Payment Service")
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final ApplicationEventPublisher eventPublisher;

	public void processPayment(UUID orderId, BigDecimal amount) {
		//PENDING 상태로 생성
		Payment payment = Payment.create(orderId, amount);
		//결제 성공 처리
		payment.complete();

		Payment savedPayment = paymentRepository.save(payment);

		//이벤트 발행
	}

}

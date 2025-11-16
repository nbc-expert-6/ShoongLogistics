package com.shoonglogitics.orderservice.domain.payment.application.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.payment.domain.entity.Payment;
import com.shoonglogitics.orderservice.domain.payment.domain.event.PaymentCompletedEvent;
import com.shoonglogitics.orderservice.domain.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Payment Service")
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public void processPayment(UUID orderId, UUID productId, BigDecimal totalPrice, BigDecimal price,
		Integer quantity) {
		//금액 계산
		BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));
		log.info("결제 금액: {}", amount.longValue());

		if (totalPrice.compareTo(BigDecimal.ZERO) < 0 || !totalPrice.equals(amount)) {
			throw new IllegalArgumentException("주문 금액이 유효하지 않습니다.");
		}
		//PENDING 상태로 생성
		Payment payment = Payment.create(orderId, amount);

		//결제 성공 처리
		payment.complete();

		Payment savedPayment = paymentRepository.save(payment);
		//이벤트 발행
		eventPublisher.publishEvent(new PaymentCompletedEvent(savedPayment, productId, quantity));
		log.info("결제 성공 이벤트 발행");
	}

}

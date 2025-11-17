package com.shoonglogitics.orderservice.domain.payment.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.payment.domain.vo.PaymentStatus;
import com.shoonglogitics.orderservice.domain.common.entity.BaseAggregateRoot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_payments")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseAggregateRoot<Payment> {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	private UUID orderId;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status; // PENDING, COMPLETED, FAILED

	private BigDecimal amount;

	public static Payment create(UUID orderId, BigDecimal amount) {
		Payment payment = new Payment();
		payment.orderId = orderId;
		payment.amount = amount;
		payment.status = PaymentStatus.PENDING;
		return payment;
	}

	public void complete() {
		this.status = PaymentStatus.COMPLETED;
	}

	public void fail() {
		this.status = PaymentStatus.FAILED;
	}
}

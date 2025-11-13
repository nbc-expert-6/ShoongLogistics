package com.shoonglogitics.notificationservice.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_ai_delivery_advice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AIDeliveryAdvice {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	private UUID orderId;

	@Column(columnDefinition = "TEXT")
	private String productInfo;

	@Column(columnDefinition = "TEXT")
	private String deliveryRequest;

	@Column(columnDefinition = "TEXT")
	private String routeInfo;

	private String workingHours;

	@Column(columnDefinition = "TEXT")
	private String aiResponse;

	private LocalDateTime finalDeadline;

	@Builder
	public AIDeliveryAdvice(UUID orderId, String productInfo, String deliveryRequest, String routeInfo,
		String workingHours,
		String aiResponse, LocalDateTime finalDeadline) {
		this.orderId = orderId;
		this.productInfo = productInfo;
		this.deliveryRequest = deliveryRequest;
		this.routeInfo = routeInfo;
		this.workingHours = workingHours;
		this.aiResponse = aiResponse;
		this.finalDeadline = finalDeadline;
	}
}

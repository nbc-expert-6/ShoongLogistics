package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.dto.ListOrderResult;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;

public record ListOrderResponse(
	UUID orderId,
	Long userId,
	UUID receiverCompanyId,
	String receiverCompanyName,
	UUID supplierCompanyId,
	String supplierCompanyName,
	String request,
	String deliveryRequest,
	String address,
	String addressDetail,
	String zipCode,
	OrderStatus status,
	LocalDateTime createdAt,
	LocalDateTime paidAt
) {
	public static ListOrderResponse from(ListOrderResult result) {
		return new ListOrderResponse(
			result.orderId(),
			result.userId(),
			result.receiverCompanyId(),
			result.receiverCompanyName(),
			result.supplierCompanyId(),
			result.supplierCompanyName(),
			result.request(),
			result.deliveryRequest(),
			result.address(),
			result.addressDetail(),
			result.zipCode(),
			result.status(),
			result.createdAt(),
			result.paidAt()
		);
	}
}

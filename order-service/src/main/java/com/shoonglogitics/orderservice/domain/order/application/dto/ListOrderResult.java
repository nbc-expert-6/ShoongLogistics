package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;

public record ListOrderResult(
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
	public static ListOrderResult from(Order order) {
		return new ListOrderResult(
			order.getId(),
			order.getUserId(),
			order.getReceiver().getCompanyId(),
			order.getReceiver().getCompanyName(),
			order.getSupplier().getCompanyId(),
			order.getSupplier().getCompanyName(),
			order.getRequest(),
			order.getDeliveryRequest(),
			order.getAddress().getAddress(),
			order.getAddress().getAddressDetail(),
			order.getAddress().getZipCode(),
			order.getStatus(),
			order.getCreatedAt(),
			order.getPaidAt()
		);
	}
}

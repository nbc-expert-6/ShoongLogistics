package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;

public record FindOrderResult(
	UUID orderId,
	UUID receiverCompanyId,
	String receiverCompanyName,
	UUID supplierCompanyId,
	String supplierCompanyName,
	List<FindOrderItemResult> items,
	OrderStatus status,
	LocalDateTime createdAt,
	LocalDateTime paidAt

) {
	public static FindOrderResult from(Order order) {
		return new FindOrderResult(
			order.getId(),
			order.getReceiver().getCompanyId(),
			order.getReceiver().getCompanyName(),
			order.getSupplier().getCompanyId(),
			order.getSupplier().getCompanyName(),
			order.getOrderItems().stream()
				.map(FindOrderItemResult::from)
				.toList(),
			order.getStatus(),
			order.getCreatedAt(),
			order.getPaidAt()

		);
	}
}

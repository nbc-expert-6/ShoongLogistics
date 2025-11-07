package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.dto.FindOrderResult;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;

public record FindOrderResponse(
	UUID orderId,
	UUID receiverCompanyId,
	String receiverCompanyName,
	UUID supplierCompanyId,
	String supplierCompanyName,
	List<FindOrderItemResponse> items,
	OrderStatus status,
	LocalDateTime createdAt,
	LocalDateTime paidAt
) {
	public static FindOrderResponse from(FindOrderResult result) {
		return new FindOrderResponse(
			result.orderId(),
			result.receiverCompanyId(),
			result.receiverCompanyName(),
			result.supplierCompanyId(),
			result.supplierCompanyName(),
			result.items().stream()
				.map(FindOrderItemResponse::from)
				.toList(),
			result.status(),
			result.createdAt(),
			result.paidAt()

		);
	}
}

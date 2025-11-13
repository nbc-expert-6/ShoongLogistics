package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.dto.FindOrderResult;

public record FindOrderResponse(
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
	Double latitude,
	Double longitude,
	List<FindOrderItemResponse> items,
	String status,
	LocalDateTime createdAt,
	LocalDateTime paidAt
) {
	public static FindOrderResponse from(FindOrderResult result) {
		return new FindOrderResponse(
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
			result.latitude(),
			result.longitude(),
			result.items().stream()
				.map(FindOrderItemResponse::from)
				.toList(),
			result.status().getDescription(),
			result.createdAt(),
			result.paidAt()

		);
	}
}

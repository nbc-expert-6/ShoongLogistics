package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

public record UpdateDeliveryRequest(
	String request,
	Long shipperId,
	String shipperName,
	String shipperPhoneNumber,
	String shipperSlackId
) {
}

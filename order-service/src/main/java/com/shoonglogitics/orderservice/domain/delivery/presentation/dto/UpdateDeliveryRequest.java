package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record UpdateDeliveryRequest(
	String request,
	UUID shipperId,
	String shipperName,
	String shipperPhoneNumber,
	String shipperSlackId
) {
}

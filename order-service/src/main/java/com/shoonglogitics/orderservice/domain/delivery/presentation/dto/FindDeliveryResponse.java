package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.FindDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;

public record FindDeliveryResponse(
	UUID deliveryId,
	UUID orderId,
	DeliveryStatus status,
	String request,
	String address,
	String addressDetail,
	String zipCode,
	Long shipperId,
	String shipperName,
	String shipperPhoneNumber,
	String receiverSlackId,
	UUID departureHubId,
	UUID destinationHubId
) {
	public static FindDeliveryResponse from(FindDeliveryResult result) {
		return new FindDeliveryResponse(
			result.deliveryId(),
			result.orderId(),
			result.status(),
			result.request(),
			result.address(),
			result.addressDetail(),
			result.zipCode(),
			result.shipperId(),
			result.shipperName(),
			result.shipperPhoneNumber(),
			result.receiverSlackId(),
			result.departureHubId(),
			result.destinationHubId()
		);
	}
}

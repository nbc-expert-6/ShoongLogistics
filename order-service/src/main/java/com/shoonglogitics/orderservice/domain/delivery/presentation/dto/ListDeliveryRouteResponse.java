package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.ListDeliveryRouteResult;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;

public record ListDeliveryRouteResponse(
	UUID deliveryRouteId,
	UUID shipperId,
	String shipperName,
	String shipperPhoneNumber,
	String shipperSlackId,
	UUID departureHubId,
	UUID arrivalHubId,
	Integer sequence,
	Integer estimatedDistance,
	Integer estimatedDuration,
	Integer actualDistance,
	Integer actualDuration,
	DeliveryStatus status
) {
	public static ListDeliveryRouteResponse from(ListDeliveryRouteResult result) {
		return new ListDeliveryRouteResponse(
			result.deliveryRouteId(),
			result.shipperId(),
			result.shipperName(),
			result.shipperPhoneNumber(),
			result.shipperSlackId(),
			result.departureHubId(),
			result.arrivalHubId(),
			result.sequence(),
			result.estimatedDistance(),
			result.estimatedDuration(),
			result.actualDistance(),
			result.actualDuration(),
			result.status()
		);
	}
}

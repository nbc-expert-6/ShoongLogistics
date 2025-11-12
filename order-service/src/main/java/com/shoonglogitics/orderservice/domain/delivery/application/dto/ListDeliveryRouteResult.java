package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;

public record ListDeliveryRouteResult(
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
	public static ListDeliveryRouteResult from(DeliveryRoute route) {
		return new ListDeliveryRouteResult(
			route.getId(),
			route.getShipperInfo().getShipperId(),
			route.getShipperInfo().getShipperName(),
			route.getShipperInfo().getShipperPhoneNumber(),
			route.getShipperInfo().getShipperSlackId(),
			route.getDepartureHubId().getHubId(),
			route.getArrivalHubId().getHubId(),
			route.getSequence(),
			route.getEstimate().getDistance(),
			route.getEstimate().getDuration(),
			route.getActual() != null ? route.getActual().getDistance() : null,
			route.getActual() != null ? route.getActual().getDuration() : null,
			route.getStatus()
		);
	}
}

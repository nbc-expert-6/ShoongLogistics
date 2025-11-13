package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;

public record FindDeliveryResult(
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
	public static FindDeliveryResult from(Delivery delivery) {
		return new FindDeliveryResult(
			delivery.getId(),
			delivery.getOrderId(),
			delivery.getStatus(),
			delivery.getRequest(),
			delivery.getAddress().getAddress(),
			delivery.getAddress().getAddressDetail(),
			delivery.getAddress().getZipCode(),
			delivery.getShipperInfo().getShipperId(),
			delivery.getShipperInfo().getShipperName(),
			delivery.getShipperInfo().getShipperPhoneNumber(),
			delivery.getShipperInfo().getShipperSlackId(),
			delivery.getDepartureHubId().getHubId(),
			delivery.getDestinationHubId().getHubId()
		);
	}
}

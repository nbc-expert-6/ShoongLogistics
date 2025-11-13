package com.shoonglogitics.userservice.application.dto;

import java.util.UUID;

import com.shoonglogitics.userservice.domain.entity.ShipperType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShipperViewResponseDto {

	private Long shipperId;
	private String name;
	private String email;
	private String phoneNumber;
	private String slackId;
	private UUID hubId;

	private ShipperType shipperType;
	private Integer shippingOrder;
	private Boolean isShippingAvailable;

	public ShipperViewResponseDto(
		Long shipperId, String name,
		String email,
		String phoneNumber,
		String slackId,
		UUID hubId, ShipperType shipperType,
		Integer shippingOrder, Boolean isShippingAvailable) {

		this.shipperId = shipperId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.slackId = slackId;
		this.hubId = hubId;
		this.shipperType = shipperType;
		this.shippingOrder = shippingOrder;
		this.isShippingAvailable = isShippingAvailable;
	}
}

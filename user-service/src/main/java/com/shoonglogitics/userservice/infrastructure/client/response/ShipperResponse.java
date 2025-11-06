package com.shoonglogitics.userservice.infrastructure.client.response;

import java.util.UUID;

import com.shoonglogitics.userservice.domain.entity.Shipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ShipperResponse extends BaseUserResponse {

	private UUID hubId;
	private String email;
	private String name;
	private String phoneNumber;
	private String slackId;
	private String shipperType;
	private Integer order;
	private Boolean isShippingAvailable;

	public static ShipperResponse from(Shipper shipper) {
		return ShipperResponse.builder()
			.userId(shipper.getId())
			.userName(shipper.getUserName())
			.userRole(shipper.getUserRole().name())
			.signupStatus(shipper.getSignupStatus().name())

			.hubId(shipper.getHubId() != null ? shipper.getHubId().getId() : null)
			.email(shipper.getEmail().getValue())
			.name(shipper.getName().getValue())
			.phoneNumber(shipper.getPhoneNumber().getValue())
			.slackId(shipper.getSlackId().getValue())
			.shipperType(shipper.getShipperType().name())
			.order(shipper.getOrder())
			.isShippingAvailable(shipper.getIsShippingAvailable())
			.deletedAt(shipper.getDeletedAt())

			.createdAt(shipper.getCreatedAt())
			.createdBy(shipper.getCreatedBy())
			.updatedAt(shipper.getUpdatedAt())
			.updatedBy(shipper.getUpdatedBy())
			.deletedAt(shipper.getDeletedAt())
			.deletedBy(shipper.getDeletedBy())
			.build();
	}

}

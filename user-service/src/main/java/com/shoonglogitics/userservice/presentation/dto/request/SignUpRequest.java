package com.shoonglogitics.userservice.presentation.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

	private String userName;
	private String password;
	private String userType; // MASTER, HUB_MANAGER, COMPANY_MANAGER, SHIPPER

	private String email;
	private String name;
	private String slackId;
	private String phoneNumber;

	private UUID hubId;       // HUB_MANAGER, COMPANY_SHIPPER
	private UUID companyId;   // COMPANY_MANAGER

	private String shipperType; // SHIPPER
	private Integer order;      // SHIPPER
	private Boolean isShippingAvailable; // SHIPPER

}

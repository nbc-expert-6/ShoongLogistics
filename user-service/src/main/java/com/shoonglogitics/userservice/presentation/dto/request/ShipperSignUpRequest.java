package com.shoonglogitics.userservice.presentation.dto.request;

import java.util.UUID;

import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;
import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipperSignUpRequest extends SignUpRequest {
	private String hubId;
	private ShipperType shipperType;
	private Boolean isShippingAvailable;

	@Override
	public Object toCommand() {
		return ShipperSignUpCommand.builder()
			.userName(getUserName())
			.password(getPassword())
			.email(new Email(getEmail()))
			.name(new Name(getName()))
			.slackId(new SlackId(getSlackId()))
			.phoneNumber(new PhoneNumber(getPhoneNumber()))
			.hubId(hubId != null ? new HubId(UUID.fromString(hubId)) : null)
			.shipperType(shipperType)
			.isShippingAvailable(isShippingAvailable)
			.build();
	}
}

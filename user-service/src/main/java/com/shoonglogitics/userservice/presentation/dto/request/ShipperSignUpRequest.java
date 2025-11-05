package com.shoonglogitics.userservice.presentation.dto.request;

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
	private HubId hubId;
	private ShipperType shipperType;
	private Integer order;
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
			.hubId(hubId != null ? hubId : null)
			.shipperType(shipperType)
			.order(order)
			.isShippingAvailable(isShippingAvailable)
			.build();
	}
}

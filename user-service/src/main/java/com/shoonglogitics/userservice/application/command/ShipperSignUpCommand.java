package com.shoonglogitics.userservice.application.command;

import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShipperSignUpCommand extends SignUpUserCommand {

	private ShipperType shipperType;
	private HubId hubId;
	private Integer order;
	private Boolean isShippingAvailable;

	@Builder
	public ShipperSignUpCommand(String userName, String password, Name name,
		Email email, SlackId slackId, PhoneNumber phoneNumber,
		ShipperType shipperType, HubId hubId,
		Integer order, Boolean isShippingAvailable) { // 필드 추가
		super(userName, password, SignupStatus.PENDING, name, email, slackId, phoneNumber);
		this.shipperType = shipperType;
		this.hubId = hubId;
		this.order = order;
		this.isShippingAvailable = isShippingAvailable;
	}

	@Override
	public UserRole getRole() {
		return UserRole.SHIPPER;
	}
}


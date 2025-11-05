package com.shoonglogitics.userservice.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;

import lombok.Getter;

@Getter
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "userType"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = MasterSignUpRequest.class, name = "MASTER"),
	@JsonSubTypes.Type(value = HubManagerSignUpRequest.class, name = "HUB_MANAGER"),
	@JsonSubTypes.Type(value = CompanyManagerSignUpCommand.class, name = "COMPANY_MANAGER"),
	@JsonSubTypes.Type(value = ShipperSignUpCommand.class, name = "SHIPPER")
})
public abstract class SignUpRequest {

	private String userName;
	private String password;

	private String email;
	private String name;
	private String slackId;
	private String phoneNumber;

	public abstract <T> T toCommand();

}

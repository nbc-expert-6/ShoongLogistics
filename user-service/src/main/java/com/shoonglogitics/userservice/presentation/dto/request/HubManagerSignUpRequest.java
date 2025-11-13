package com.shoonglogitics.userservice.presentation.dto.request;

import java.util.UUID;

import com.shoonglogitics.userservice.application.command.HubManagerSignUpCommand;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubManagerSignUpRequest extends SignUpRequest {
	private String hubId;

	@Override
	public HubManagerSignUpCommand toCommand() {
		return HubManagerSignUpCommand.builder()
			.userName(getUserName())
			.password(getPassword())
			.email(new Email(getEmail()))
			.name(new Name(getName()))
			.slackId(new SlackId(getSlackId()))
			.phoneNumber(new PhoneNumber(getPhoneNumber()))
			.hubId(new HubId(UUID.fromString(hubId)))
			.build();
	}
}

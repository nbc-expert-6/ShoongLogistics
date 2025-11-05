package com.shoonglogitics.userservice.presentation.dto.request;

import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.domain.vo.CompanyId;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanayManagerSignUpRequest extends SignUpRequest {
	private CompanyId companyId;

	@Override
	public Object toCommand() {
		return CompanyManagerSignUpCommand.builder()
			.userName(getUserName())
			.password(getPassword())
			.email(new Email(getEmail()))
			.name(new Name(getName()))
			.slackId(new SlackId(getSlackId()))
			.phoneNumber(new PhoneNumber(getPhoneNumber()))
			.companyId(companyId)
			.build();
	}
}

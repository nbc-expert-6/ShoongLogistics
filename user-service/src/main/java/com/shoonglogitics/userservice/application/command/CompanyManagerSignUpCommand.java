package com.shoonglogitics.userservice.application.command;

import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.vo.CompanyId;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyManagerSignUpCommand extends SignUpUserCommand {

	private CompanyId companyId;

	@Builder
	public CompanyManagerSignUpCommand(String userName, String password, Name name,
		Email email, SlackId slackId, PhoneNumber phoneNumber,
		CompanyId companyId) {
		super(userName, password, SignupStatus.PENDING, name, email, slackId, phoneNumber);
		this.companyId = companyId;
	}

	@Override
	public UserRole getRole() {
		return UserRole.COMPANY_MANAGER;
	}

}

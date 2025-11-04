package com.shoonglogitics.userservice.application.command;

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
public class HubManagerSignUpCommand extends SignUpUserCommand {

	private HubId hubId;

	@Builder
	HubManagerSignUpCommand(String userName, String password, Name name,
		Email email, SlackId slackId,
		PhoneNumber phoneNumber, HubId hubId) {
		super(userName, password, SignupStatus.PENDING, name, email, slackId, phoneNumber);
		this.hubId = hubId;
	}

	@Override
	public UserRole getRole() {
		return UserRole.HUB_MANAGER;
	}

}

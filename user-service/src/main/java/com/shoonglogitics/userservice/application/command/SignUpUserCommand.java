package com.shoonglogitics.userservice.application.command;

import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import lombok.Getter;

@Getter
public abstract class SignUpUserCommand {

	private String userName;
	private String password;
	private SignupStatus signupStatus;
	private Name name;
	private Email email;
	private SlackId slackId;
	private PhoneNumber phoneNumber;

	protected SignUpUserCommand(String userName, String password,
		SignupStatus signupStatus, Name name, Email email, SlackId slackId,
		PhoneNumber phoneNumber) {
		this.userName = userName;
		this.password = password;
		this.signupStatus = signupStatus;
		this.name = name;
		this.email = email;
		this.slackId = slackId;
		this.phoneNumber = phoneNumber;
	}

	public abstract UserRole getRole();

}

package com.shoonglogitics.userservice.presentation.dto.request;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;

public record LoginRequest(String userName, String password) {

	public static LoginUserCommand from(LoginRequest dto) {
		return new LoginUserCommand(dto.userName(), dto.password());
	}

}


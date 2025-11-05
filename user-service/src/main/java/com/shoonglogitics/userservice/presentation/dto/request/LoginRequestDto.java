package com.shoonglogitics.userservice.presentation.dto.request;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;

public record LoginRequestDto(String userName, String password) {

	public static LoginUserCommand from(LoginRequestDto dto) {
		return new LoginUserCommand(dto.userName(), dto.password());
	}

}


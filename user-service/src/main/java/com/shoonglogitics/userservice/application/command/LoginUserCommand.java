package com.shoonglogitics.userservice.application.command;

import com.shoonglogitics.userservice.presentation.dto.request.LoginRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserCommand {

	private String userName;
	private String password;

	public static LoginUserCommand from(LoginRequestDto dto) {
		LoginUserCommand command = new LoginUserCommand();
		command.userName = dto.userName();
		command.password = dto.password();
		return command;
	}

}

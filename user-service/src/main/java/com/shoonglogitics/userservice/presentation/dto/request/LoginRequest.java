package com.shoonglogitics.userservice.presentation.dto.request;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

	@NotBlank(message = "사용자 이름은 필수입니다.")
	String userName,

	@NotBlank(message = "비밀번호는 필수입니다.")
	String password) {

	public LoginUserCommand toCommand() {
		return new LoginUserCommand(userName, password);
	}

}


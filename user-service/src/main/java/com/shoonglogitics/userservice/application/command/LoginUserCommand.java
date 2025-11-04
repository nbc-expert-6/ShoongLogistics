package com.shoonglogitics.userservice.application.command;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserCommand {

	private String userName;
	private String password;

}

package com.shoonglogitics.userservice.application.strategy.signup;

import com.shoonglogitics.userservice.application.command.SignUpUserCommand;

public interface SignUpStrategy {

	void signUp(SignUpUserCommand signUpUserCommand);
}

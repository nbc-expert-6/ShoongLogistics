package com.shoonglogitics.userservice.presentation.dto.request;

import com.shoonglogitics.userservice.application.command.UpdateUserCommand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

	private String name;
	private String email;
	private String slackId;
	private String phoneNumber;

	public UpdateUserCommand toCommand() {
		return new UpdateUserCommand(name, email, slackId, phoneNumber);
	}

}

package com.shoonglogitics.userservice.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {

	private String name;

	private String email;

	private String slackId;

	private String phoneNumber;

}

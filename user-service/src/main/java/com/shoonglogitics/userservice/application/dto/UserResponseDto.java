package com.shoonglogitics.userservice.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserResponseDto {

	private Long userId;
	private String username;
	private String role;
	private String signupStatus;

	public UserResponseDto(Long userId, String username, String role, String signupStatus) {
		this.userId = userId;
		this.username = username;
		this.role = role;
		this.signupStatus = signupStatus;
	}

}

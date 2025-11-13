package com.shoonglogitics.userservice.application.dto;

public record LoginUserResponseDto(String accessToken, Long userId) {

	public static LoginUserResponseDto of(String accessToken, Long userId) {
		return new LoginUserResponseDto(accessToken, userId);
	}

}

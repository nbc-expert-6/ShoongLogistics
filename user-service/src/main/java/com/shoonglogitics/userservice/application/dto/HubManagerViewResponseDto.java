package com.shoonglogitics.userservice.application.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class HubManagerViewResponseDto {

	private Long hubManagerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String slackId;
	private UUID hubId;  // HubManager 전용

	public HubManagerViewResponseDto(
		Long hubManagerId,
		String name,
		String email,
		String phoneNumber,
		String slackId,
		UUID hubId) {

		this.hubManagerId = hubManagerId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.slackId = slackId;
		this.hubId = hubId;
	}

}

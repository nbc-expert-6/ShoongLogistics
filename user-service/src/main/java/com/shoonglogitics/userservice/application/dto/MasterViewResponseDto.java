package com.shoonglogitics.userservice.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class MasterViewResponseDto {

	private Long masterId;
	private String name;
	private String email;
	private String phoneNumber;
	private String slackId;

	public MasterViewResponseDto(
		Long masterId,
		String name,
		String email,
		String phoneNumber,
		String slackId) {

		this.masterId = masterId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.slackId = slackId;
	}

}

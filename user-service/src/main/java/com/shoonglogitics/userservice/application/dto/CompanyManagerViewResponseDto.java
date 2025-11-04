package com.shoonglogitics.userservice.application.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyManagerViewResponseDto {

	private Long companyManagerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String slackId;
	private UUID companyId;

	public CompanyManagerViewResponseDto(
		Long companyManagerId,
		String name,
		String email,
		String phoneNumber,
		String slackId,
		UUID companyId) {

		this.companyManagerId = companyManagerId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.slackId = slackId;
		this.companyId = companyId;
	}
}

package com.shoonglogitics.userservice.infrastructure.client.response;

import java.util.UUID;

import com.shoonglogitics.userservice.domain.entity.CompanyManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyManagerResponse extends BaseUserResponse {

	private UUID companyId;
	private String email;
	private String name;
	private String phoneNumber;
	private String slackId;

	public static CompanyManagerResponse from(CompanyManager companyManager) {
		return CompanyManagerResponse.builder()
			.userId(companyManager.getId())
			.userName(companyManager.getUserName())
			.userRole(companyManager.getUserRole().name())
			.signupStatus(companyManager.getSignupStatus().name())

			.companyId(companyManager.getCompanyId().getId())
			.email(companyManager.getEmail().getValue())
			.name(companyManager.getName().getValue())
			.phoneNumber(companyManager.getPhoneNumber().getValue())
			.slackId(companyManager.getSlackId().getValue())

			.createdAt(companyManager.getCreatedAt())
			.createdBy(companyManager.getCreatedBy())
			.updatedAt(companyManager.getUpdatedAt())
			.updatedBy(companyManager.getUpdatedBy())
			.deletedAt(companyManager.getDeletedAt())
			.deletedBy(companyManager.getDeletedBy())
			.build();
	}

}

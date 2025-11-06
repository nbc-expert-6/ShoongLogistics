package com.shoonglogitics.userservice.infrastructure.client.response;

import java.util.UUID;

import com.shoonglogitics.userservice.domain.entity.HubManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HubManagerResponse extends BaseUserResponse {

	private UUID hubId;
	private String email;
	private String name;
	private String phoneNumber;
	private String slackId;

	public static HubManagerResponse from(HubManager hubManager) {
		return HubManagerResponse.builder()
			.userId(hubManager.getId())
			.userName(hubManager.getUserName())
			.userRole(hubManager.getUserRole().name())
			.signupStatus(hubManager.getSignupStatus().name())

			.hubId(hubManager.getHubId().getId())
			.email(hubManager.getEmail().getValue())
			.name(hubManager.getName().getValue())
			.phoneNumber(hubManager.getPhoneNumber().getValue())
			.slackId(hubManager.getSlackId().getValue())
			.deletedAt(hubManager.getDeletedAt())

			.createdAt(hubManager.getCreatedAt())
			.createdBy(hubManager.getCreatedBy())
			.updatedAt(hubManager.getUpdatedAt())
			.updatedBy(hubManager.getUpdatedBy())
			.deletedAt(hubManager.getDeletedAt())
			.deletedBy(hubManager.getDeletedBy())
			.build();
	}

}

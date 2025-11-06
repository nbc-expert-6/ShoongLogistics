package com.shoonglogitics.userservice.infrastructure.client.response;

import java.time.LocalDateTime;

import com.shoonglogitics.userservice.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserResponse {

	private Long userId;
	private String userName;
	private String userRole;
	private String signupStatus;

	private LocalDateTime createdAt;
	private Long createdBy;
	private LocalDateTime updatedAt;
	private Long updatedBy;
	private LocalDateTime deletedAt;
	private Long deletedBy;

	public static BaseUserResponse from(User user) {
		return BaseUserResponse.builder()
			.userId(user.getId())
			.userName(user.getUserName())
			.userRole(user.getUserRole().name())
			.signupStatus(user.getSignupStatus().name())
			.createdAt(user.getCreatedAt())
			.createdBy(user.getCreatedBy())
			.updatedAt(user.getUpdatedAt())
			.updatedBy(user.getUpdatedBy())
			.deletedAt(user.getDeletedAt())
			.deletedBy(user.getDeletedBy())
			.build();
	}
}

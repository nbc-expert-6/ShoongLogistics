package com.shoonglogitics.companyservice.domain.common.vo;

import lombok.Getter;

@Getter
public class AuthUser {
	private final Long userId;
	private final UserRoleType role;

	private AuthUser(Long userId, UserRoleType role) {
		this.userId = userId;
		this.role = role;
	}

	public static AuthUser of(Long userId, String roleString) {
		UserRoleType role = UserRoleType.valueOf(roleString);
		return new AuthUser(userId, role);
	}

	public String getAuthority() {
		return role.getAuthority();
	}

	public boolean isHubManager() {
		return role == UserRoleType.HUB_MANAGER;
	}
}

package com.shoonglogitics.userservice.domain.entity;

import lombok.Getter;

@Getter
public enum UserRole {
	MASTER(Authority.MASTER),
	HUB_MANAGER(Authority.HUB_MANAGER),
	COMPANY_MANAGER(Authority.COMPANY_MANAGER),
	SHIPPER(Authority.SHIPPER);

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String MASTER = "ROLE_MASTER";
		public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
		public static final String COMPANY_MANAGER = "ROLE_COMPANY_MANAGER";
		public static final String SHIPPER = "ROLE_SHIPPER";
	}
}

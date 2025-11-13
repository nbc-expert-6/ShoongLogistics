package com.shoonglogitics.companyservice.domain.common.vo;

import lombok.Getter;

@Getter
public enum UserRoleType {
	MASTER(Authority.MASTER),
	HUB_MANAGER(Authority.HUB_MANAGER),
	SHIPPER(Authority.SHIPPER),
	COMPANY_MANAGER(Authority.COMPANY_MANAGER);

	private final String authority;

	UserRoleType(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String MASTER = "ROLE_MASTER";
		public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
		public static final String SHIPPER = "ROLE_SHIPPER";
		public static final String COMPANY_MANAGER = "ROLE_COMPANY_MANAGER";
	}
}

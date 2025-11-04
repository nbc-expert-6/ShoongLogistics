package com.shoonglogitics.companyservice.domain.common.vo;

import lombok.Getter;

@Getter
public enum UserRoleType {
	MASTER(Authority.MASTER),
	HUB_MANAGER(Authority.HUB_MANAGER),
	DELIVERY_MANAGER(Authority.DELIVERY_MANAGER),
	SUPPLIER_MANAGER(Authority.SUPPLIER_MANAGER);

	private final String authority;

	UserRoleType(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String MASTER = "ROLE_MASTER";
		public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
		public static final String DELIVERY_MANAGER = "ROLE_DELIVERY_MANAGER";
		public static final String SUPPLIER_MANAGER = "ROLE_SUPPLIER_MANAGER";
	}
}

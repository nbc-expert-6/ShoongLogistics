package com.shoonglogitics.userservice.domain.entity;

import lombok.Getter;

@Getter
public enum SignupStatus {
	PENDING, // 승인 대기 상태
	APPROVED, // 승인 상태
	REJECTED; // 거절 상태
}

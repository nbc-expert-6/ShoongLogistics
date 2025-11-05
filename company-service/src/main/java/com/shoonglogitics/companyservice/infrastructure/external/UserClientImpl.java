package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
	private final UserFeignClient userFeignClient;

	@Override
	public boolean isHubManager(Long userId, UUID hubId) {
		ApiResponse<UserInfo> response = userFeignClient.getUserInfo(userId);

		if (!response.success()) {
			log.warn("사용자 정보 조회 실패 - userId: {}, message: {}", userId, response.message());
			throw new IllegalArgumentException(response.message());
		}
		UserInfo data = response.data();
		return data.hubId().equals(hubId);
	}
}

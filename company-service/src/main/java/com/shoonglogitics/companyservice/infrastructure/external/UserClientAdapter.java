package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.application.service.dto.CompanyManagerInfo;
import com.shoonglogitics.companyservice.application.service.dto.HubManagerInfo;
import com.shoonglogitics.companyservice.infrastructure.external.dto.UserInfoFeignClientResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClient {
	private final UserFeignClient userFeignClient;

	@Override
	public List<CompanyManagerInfo> getCompanyManagerInfos(UUID companyId) {
		ApiResponse<List<UserInfoFeignClientResponse>> response = userFeignClient.getUserInfos(null, companyId);

		if (!response.success() || response.data() == null || response.data().isEmpty()) {
			log.warn("사용자 정보 조회 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}
		return response.data().stream()
			.map(this::toCompanyManagerInfo)
			.toList();
	}

	@Override
	public List<HubManagerInfo> getHubManagerInfos(UUID hubId) {
		ApiResponse<List<UserInfoFeignClientResponse>> response = userFeignClient.getUserInfos(hubId, null);

		if (!response.success() || response.data() == null || response.data().isEmpty()) {
			log.warn("사용자 정보 조회 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}
		return response.data().stream()
			.map(this::toHubManagerInfo)
			.toList();
	}

	private HubManagerInfo toHubManagerInfo(UserInfoFeignClientResponse response) {
		return new HubManagerInfo(
			response.userId(),
			response.companyId()
		);
	}

	private CompanyManagerInfo toCompanyManagerInfo(UserInfoFeignClientResponse response) {
		return new CompanyManagerInfo(
			response.userId(),
			response.companyId()
		);
	}

	/**
	 * @param userId 삭제할 업체 담당자 유저 ID
	 * @return 업체 담당자 삭제 요청 성공 여부
	 */
	@Override
	public boolean deleteCompanyManager(Long userId) {
		return userFeignClient.deleteUser(userId).success();
	}
}
package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
	private final UserFeignClient userFeignClient;

	@Override
	public boolean isHubManager(AuthUser authUser, UUID hubId) {
		ApiResponse<UserInfo> response = userFeignClient.getUserInfo(authUser.getUserId(), authUser.getUserId(),
			authUser.getAuthority());

		if (!response.success()) {
			log.warn("사용자 정보 조회 실패 - userId: {}, message: {}", authUser.getUserId(), response.message());
			throw new IllegalArgumentException(response.message());
		}
		UserInfo data = response.data();
		return data.hubId().equals(hubId);
	}

	@Override
	public boolean isCompanyManager(AuthUser authUser, UUID companyId) {
		ApiResponse<UserInfo> response = userFeignClient.getUserInfo(authUser.getUserId(), authUser.getUserId(),
			authUser.getAuthority());

		if (!response.success() || response.data().companyId() == null) {
			log.warn("사용자 정보 조회 실패 - userId: {}, message: {}", authUser.getUserId(), response.message());
			throw new IllegalArgumentException(response.message());
		}
		UserInfo data = response.data();
		return data.companyId().equals(companyId);
	}

	/**
	 * @param companyId 업체 ID
	 * @return 업체 담당자 삭제 요청 성공 여부
	 */
	@Override
	public boolean deleteCompanyManager(AuthUser authUser, UUID companyId) {
		ApiResponse<PageResponse<UserInfo>> response = userFeignClient.getUserInfos(companyId, false,
			authUser.getUserId(), authUser.getAuthority());

		if (!response.success() || response.data().getContent().isEmpty() || response.data().getContent() == null) {
			log.warn("사용자 정보 조회 실패 - companyId: {}, message: {}", companyId, response.message());
			throw new IllegalArgumentException(response.message());
		}

		List<UserInfo> users = response.data().getContent();
		Optional<UserInfo> companyManager = users.stream().findFirst();

		return companyManager
			.map(m -> userFeignClient.deleteUser(m.userId()).success())
			.orElse(false);
	}

	@Override
	public boolean updateCompanyLocation(AuthUser authUser, UUID companyId, GeoLocation location) {
		//어떻게 주고받을지 결정 후 변경
		return false;
	}
}
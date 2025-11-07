package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.infrastructure.security.HeaderType;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageResponse;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
	/**
	 * User Service에서 사용자 정보 조회
	 */
	@GetMapping("/api/v1/users/{userId}")
	ApiResponse<UserClient.UserInfo> getUserInfo(@PathVariable("userId") Long userId,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) String currentUserRole);

	@GetMapping("/api/v1/users")
	ApiResponse<PageResponse<UserClient.UserInfo>> getUserInfos(@RequestParam("companyId") UUID companyId,
		@RequestParam("isDeleted") Boolean isDeleted,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) String currentUserRole);

	@DeleteMapping("/api/v1/users/{userId}")
	ApiResponse<String> deleteUser(@PathVariable("userId") Long userId);

	/*TODO: 해당 API의 경우 허브쪽에 업체 배송지 변경을 알리는 API입니다. 추후에 허브쪽과 소통해서 어떻게 주고 받을지 정해야 합니다.
	 */

	@PostMapping("/api/v1/hubs/{hubId}")
	ApiResponse<String> updateCompanyLocation(
		@PathVariable("hubId") Long hubId,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) String currentUserRole);
}

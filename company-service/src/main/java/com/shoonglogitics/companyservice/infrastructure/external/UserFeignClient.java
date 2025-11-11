package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoonglogitics.companyservice.infrastructure.external.dto.UserInfoFeignClientResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
	/**
	 * User Service에서 사용자 정보 조회
	 */
	@GetMapping("/api/v1/users/internal")
	ApiResponse<List<UserInfoFeignClientResponse>> getUserInfos(@RequestParam("hubId") UUID hubId,
		@RequestParam("companyId") UUID companyId);

	@DeleteMapping("/api/v1/users/{userId}")
	ApiResponse<String> deleteUser(@PathVariable("userId") Long userId);
}

package com.shoonglogitics.companyservice.infrastructure.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
	/**
	 * User Service에서 사용자 정보 조회
	 */
	@GetMapping("/api/v1/users/{userId}")
	ApiResponse<UserClient.UserInfo> getUserInfo(@PathVariable("userId") Long userId);
}

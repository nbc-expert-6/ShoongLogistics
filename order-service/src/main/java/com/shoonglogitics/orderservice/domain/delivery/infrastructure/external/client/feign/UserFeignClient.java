package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignUserResponse;
import com.shoonglogitics.orderservice.domain.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@FeignClient(
	name = "user-service",
	url = "${user-service.url}"
)
public interface UserFeignClient {

	@GetMapping("/api/v1/users")
	ResponseEntity<ApiResponse<List<FeignUserResponse>>> getShippers(
		@RequestParam("hubId") UUID hubId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);

	@GetMapping("/api/v1/users/internal")
	ResponseEntity<ApiResponse<List<FeignUserResponse>>> getInternalUsers(
		@RequestParam(required = false) UUID hubId,
		@RequestParam(required = false) UUID companyId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role
	);
}

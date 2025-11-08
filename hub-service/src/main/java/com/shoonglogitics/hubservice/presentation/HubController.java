package com.shoonglogitics.hubservice.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.hubservice.application.HubService;
import com.shoonglogitics.hubservice.application.command.CreateHubCommand;
import com.shoonglogitics.hubservice.application.dto.HubSummary;
import com.shoonglogitics.hubservice.domain.common.vo.AuthUser;
import com.shoonglogitics.hubservice.presentation.dto.ApiResponse;
import com.shoonglogitics.hubservice.presentation.dto.request.CreateHubRequest;
import com.shoonglogitics.hubservice.presentation.dto.response.CreateHubResponse;
import com.shoonglogitics.hubservice.presentation.dto.response.HubDetailResponse;
import com.shoonglogitics.hubservice.presentation.dto.response.HubListResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
@Validated
public class HubController {

	private final HubService hubService;

	@PostMapping
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<ApiResponse<CreateHubResponse>> createHub(@Valid @RequestBody CreateHubRequest request) {

		CreateHubCommand command = new CreateHubCommand(
			request.name(),
			request.address(),
			request.latitude(),
			request.longitude(),
			request.hubType()
		);

		CreateHubResponse response = CreateHubResponse.from(hubService.createHub(command));
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/{hubId}")
	@PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','SHIPPER','COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<HubDetailResponse>> getHub(@PathVariable UUID hubId) {
		HubDetailResponse response = HubDetailResponse.from(hubService.getHub(hubId));
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','SHIPPER','COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<HubListResponse>> getHubs() {
		List<HubSummary> hubs = hubService.getAllHubs();
		HubListResponse response = HubListResponse.of(hubs);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@DeleteMapping("/{hubId}")
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<ApiResponse<Void>> deleteHub(@PathVariable UUID hubId,
		@AuthenticationPrincipal AuthUser authUser) {
		hubService.deleteHub(hubId, authUser.getUserId());
		return ResponseEntity.ok(ApiResponse.success("삭제 완료"));
	}
}

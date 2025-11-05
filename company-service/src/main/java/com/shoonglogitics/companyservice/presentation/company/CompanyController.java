package com.shoonglogitics.companyservice.presentation.company;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.companyservice.application.CompanyService;
import com.shoonglogitics.companyservice.application.command.CreateCompanyCommand;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyRequest;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Validated
public class CompanyController {
	private final CompanyService companyService;

	@PostMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<CreateCompanyResponse>> createOrder(
		@Valid @RequestBody CreateCompanyRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		CreateCompanyCommand command = CreateCompanyCommand.builder()
			.authUser(authUser)
			.hubId(request.hubId())
			.currentUserId(authUser.getUserId())
			.name(request.name())
			.address(request.address())
			.addressDetail(request.addressDetail())
			.zipCode(request.zipCode())
			.latitude(request.latitude())
			.longitude(request.longitude())
			.type(request.type())
			.build();

		UUID companyId = companyService.createCompany(command);
		CreateCompanyResponse response = new CreateCompanyResponse(
			companyId,
			"업체가 정상적으로 생성 되었습니다."
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}
}

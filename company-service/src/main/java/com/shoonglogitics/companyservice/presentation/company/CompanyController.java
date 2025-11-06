package com.shoonglogitics.companyservice.presentation.company;

import java.util.UUID;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.companyservice.application.CompanyService;
import com.shoonglogitics.companyservice.application.command.CreateCompanyCommand;
import com.shoonglogitics.companyservice.application.command.DeleteCompanyCommand;
import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyRequest;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.FindCompanyResponse;

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
	public ResponseEntity<ApiResponse<CreateCompanyResponse>> createCompany(
		@Valid @RequestBody CreateCompanyRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		CreateCompanyCommand command = CreateCompanyCommand.builder()
			.authUser(authUser)
			.hubId(request.hubId())
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

	@DeleteMapping("/{companyId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<String>> deleteCompany(
		@PathVariable UUID companyId,
		@RequestParam UUID hubId,
		@AuthenticationPrincipal AuthUser authUser) {
		DeleteCompanyCommand command = new DeleteCompanyCommand(authUser, companyId, hubId);

		companyService.deleteCompany(command);

		String responseMessage = "업체가 정상적으로 삭제 되었습니다.";

		return ResponseEntity.ok().body(ApiResponse.success(responseMessage));
	}

	@GetMapping("/{companyId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindCompanyResponse>> getCompany(
		@PathVariable UUID companyId) {

		CompanyResult result = companyService.getCompany(companyId);
		FindCompanyResponse response = FindCompanyResponse.from(result);

		return ResponseEntity.ok(ApiResponse.success(response));
	}
}

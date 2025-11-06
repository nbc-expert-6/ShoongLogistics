package com.shoonglogitics.companyservice.presentation.company;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.companyservice.application.CompanyService;
import com.shoonglogitics.companyservice.application.command.CreateCompanyCommand;
import com.shoonglogitics.companyservice.application.command.GetCompaniesCommand;
import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;
import com.shoonglogitics.companyservice.presentation.company.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageRequest;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyRequest;
import com.shoonglogitics.companyservice.presentation.company.dto.CreateCompanyResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.FindCompanyResponse;
import com.shoonglogitics.companyservice.presentation.company.dto.ListCompanyResponse;

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

	@GetMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<PageResponse<ListCompanyResponse>>> getCompanies(
		@RequestParam(required = false) UUID hubId,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) CompanyType type,
		@ModelAttribute PageRequest pageRequest) {

		GetCompaniesCommand command = GetCompaniesCommand.builder()
			.hubId(hubId)
			.name(name)
			.type(type)
			.pageRequest(pageRequest)
			.build();
		Page<CompanyResult> results = companyService.getCompanies(command);
		
		Page<ListCompanyResponse> responses = results.map(ListCompanyResponse::from);
		return ResponseEntity.ok(ApiResponse.success(PageResponse.of(responses)));
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

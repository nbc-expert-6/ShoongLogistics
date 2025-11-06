package com.shoonglogitics.companyservice.application;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.companyservice.application.command.CreateCompanyCommand;
import com.shoonglogitics.companyservice.application.command.DeleteCompanyCommand;
import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.repository.CompanyRepository;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyAddress;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
	private final CompanyRepository companyRepository;
	private final UserClient userClient;

	@Transactional
	public UUID createCompany(CreateCompanyCommand command) {
		validateHubManager(command.authUser(), command.hubId());
		validateDuplicateCompany(command.name(), command.zipCode(), command.type());

		CompanyAddress address = CompanyAddress.of(command.address(), command.addressDetail(), command.zipCode());
		GeoLocation location = GeoLocation.of(command.latitude(), command.longitude());
		Company company = Company.create(command.hubId(), command.name(), address, location, command.type());

		companyRepository.save(company);
		return company.getId();
	}

	@Transactional
	public void deleteCompany(DeleteCompanyCommand command) {
		validateHubManager(command.authUser(), command.hubId());
		Company company = companyRepository.findById(command.companyId())
			.orElseThrow(() -> new NoSuchElementException("해당 업체를 찾을 수 없습니다."));

		company.delete(command.authUser());
	}

	private void validateHubManager(AuthUser authUser, UUID hubId) {
		if (authUser.getRole().isHubManager() && !userClient.isHubManager(authUser, hubId)) {
			throw new IllegalArgumentException("해당 허브의 담당자만 업체를 생성, 삭제 할 수 있습니다.");
		}
	}

	private void validateDuplicateCompany(String name, String zipCode, CompanyType type) {
		companyRepository.findByNameAndZipCodeAndType(name, zipCode, type).ifPresent(company -> {
			throw new IllegalArgumentException("이미 존재하는 업체입니다.");
		});
	}

	public CompanyResult getCompany(UUID companyId) {
		Company company = companyRepository.findById(companyId)
			.orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다."));

		return CompanyResult.from(company);
	}
}

package com.shoonglogitics.companyservice.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.shoonglogitics.companyservice.application.command.CreateCompanyCommand;
import com.shoonglogitics.companyservice.application.command.CreateProductCommand;
import com.shoonglogitics.companyservice.application.command.DeleteCompanyCommand;
import com.shoonglogitics.companyservice.application.command.GetCompaniesCommand;
import com.shoonglogitics.companyservice.application.command.UpdateCompanyCommand;
import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.application.service.dto.CompanyManagerInfo;
import com.shoonglogitics.companyservice.application.service.dto.HubManagerInfo;
import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.entity.Product;
import com.shoonglogitics.companyservice.domain.company.repository.CompanyRepository;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyAddress;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;
import com.shoonglogitics.companyservice.domain.company.vo.ProductInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
	private final CompanyRepository companyRepository;
	private final UserClient userClient;

	@Transactional
	public UUID createCompany(CreateCompanyCommand command) {
		validateHubManager(command.authUser().getUserId(), command.hubId());
		validateDuplicateCompany(command.name(), command.zipCode(), command.type());

		CompanyAddress address = CompanyAddress.of(command.address(), command.addressDetail(), command.zipCode());
		GeoLocation location = GeoLocation.of(command.latitude(), command.longitude());
		Company company = Company.create(command.hubId(), command.name(), address, location, command.type());

		companyRepository.save(company);
		return company.getId();
	}

	@Transactional
	public void deleteCompany(DeleteCompanyCommand command) {
		validateHubManager(command.authUser().getUserId(), command.hubId());
		Company company = companyRepository.findById(command.companyId())
			.orElseThrow(() -> new NoSuchElementException("해당 업체를 찾을 수 없습니다."));

		company.delete(command.authUser().getUserId());
	}

	@Transactional
	public UUID updateCompany(UpdateCompanyCommand command) {
		Company company = companyRepository.findById(command.companyId())
			.orElseThrow(() -> new NoSuchElementException("해당 업체를 찾을 수 없습니다."));
		validateHubManager(command.authUser().getUserId(), company.getHubId());
		validateCompanyManager(command.authUser().getUserId(), command.companyId());

		CompanyAddress address = CompanyAddress.of(command.address(), command.addressDetail(), command.zipCode());
		GeoLocation location = GeoLocation.of(command.latitude(), command.longitude());
		company.update(command.name(), address, location, command.type());

		return company.getId();
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

	public Page<CompanyResult> getCompanies(GetCompaniesCommand command) {
		Page<Company> companies = companyRepository.getCompanies(command.hubId(), command.name(), command.type(),
			command.pageRequest().toPageable());
		return companies.map(CompanyResult::from);
	}

	@Transactional
	public UUID createProduct(CreateProductCommand command) {
		Company company = companyRepository.findById(command.companyId())
			.orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다."));

		validateCompanyManager(command.authUser().getUserId(), command.companyId());
		validateHubManager(command.authUser().getUserId(), company.getHubId());

		//TODO: productCategory api 완성되면 카테고리쪽에 api로 id존재 여부 확인 필요

		ProductInfo productInfo = ProductInfo.of(command.name(), command.price(), command.description());
		Product product= company.createProduct(command.productCategoryId(), productInfo);
		return product.getId();
	}

	private void validateHubManager(Long currentUserId, UUID hubId) {
		List<HubManagerInfo> managerInfos = userClient.getHubManagerInfos(hubId);
		boolean isManager = managerInfos.stream()
			.anyMatch(info -> info.userId().equals(currentUserId));

		if (!isManager) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN,
				"해당 허브의 담당자만 업체를 생성, 수정, 삭제 할 수 있습니다."
			);
		}
	}

	private void validateCompanyManager(Long currentUserId, UUID companyId) {
		List<CompanyManagerInfo> managerInfos = userClient.getCompanyManagerInfos(companyId);
		boolean isManager = managerInfos.stream()
			.anyMatch(info -> info.userId().equals(currentUserId));
		if (!isManager) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN,
				"해당 업체의 담당자만 업체를 수정 할 수 있습니다."
			);
		}
	}
}

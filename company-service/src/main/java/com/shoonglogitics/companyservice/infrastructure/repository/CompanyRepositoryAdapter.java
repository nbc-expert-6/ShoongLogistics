package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.entity.Product;
import com.shoonglogitics.companyservice.domain.company.repository.CompanyRepository;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {
	private final JpaCompanyRepository jpaCompanyRepository;

	@Override
	public Company save(Company company) {
		return jpaCompanyRepository.save(company);
	}

	@Override
	public Optional<Company> findById(UUID id) {
		return jpaCompanyRepository.findById(id);
	}

	@Override
	public Optional<Company> findByNameAndZipCodeAndType(String name, String zipCode, CompanyType type) {
		return jpaCompanyRepository.findByNameAndAddress_ZipCodeAndType(name, zipCode, type);
	}

	@Override
	public Page<Company> getCompanies(UUID hubId, String name, CompanyType type, Pageable pageable) {
		return jpaCompanyRepository.getCompanies(hubId, name, type, pageable);
	}

	@Override
	public Optional<Company> findByIdAndProductIdWithProduct(UUID companyId, UUID productId) {
		return jpaCompanyRepository.findByIdAndProductIdWithProduct(companyId, productId);
	}

	@Override
	public Page<Product> searchProducts(UUID companyId, List<UUID> productCategoryIds, Pageable pageable) {
		return jpaCompanyRepository.findProductsByCompanyId(companyId, productCategoryIds, pageable);
	}

}

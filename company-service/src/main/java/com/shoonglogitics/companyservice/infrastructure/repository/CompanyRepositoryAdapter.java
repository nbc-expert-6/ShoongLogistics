package com.shoonglogitics.companyservice.infrastructure.repository;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {
	private final JpaCompanyRepository jpaCompanyRepository;

	@Override
	public Company save(Company company) {
		return jpaCompanyRepository.save(company);
	}
}

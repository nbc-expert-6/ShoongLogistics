package com.shoonglogitics.companyservice.domain.company.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

public interface CompanyRepository {
	Company save(Company company);

	Optional<Company> findById(UUID id);

	Optional<Company> findByNameAndZipCodeAndType(String name, String zipCode, CompanyType type);

	Page<Company> getCompanies(UUID hubId, String name, CompanyType type, Pageable pageable);
}

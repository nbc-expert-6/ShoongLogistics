package com.shoonglogitics.companyservice.domain.company.repository;

import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.company.entity.Company;

public interface CompanyRepository {
	Company save(Company company);

	Optional<Company> findById(UUID id);

	Optional<Company> findByNameAndZipCodeAndType(String name, String zipCode, CompanyType type);
}

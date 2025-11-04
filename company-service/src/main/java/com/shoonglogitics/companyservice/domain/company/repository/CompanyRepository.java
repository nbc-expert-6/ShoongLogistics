package com.shoonglogitics.companyservice.domain.company.repository;

import com.shoonglogitics.companyservice.domain.company.entity.Company;

public interface CompanyRepository {
	Company save(Company company);
}

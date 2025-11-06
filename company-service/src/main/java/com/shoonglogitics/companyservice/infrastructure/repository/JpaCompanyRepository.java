package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {
	Optional<Company> findByNameAndAddress_ZipCodeAndType(String name, String zipCode, CompanyType type);
}

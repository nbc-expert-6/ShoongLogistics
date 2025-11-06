package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {

	@Query("SELECT c FROM Company c WHERE " +
		"(:hubId IS NULL OR c.hubId = :hubId) AND " +
		"(:name IS NULL OR c.name LIKE %:name%) AND " +
		"(:type IS NULL OR c.type = :type)")
	Page<Company> getCompanies(
		@Param("hubId") UUID hubId,
		@Param("name") String name,
		@Param("type") CompanyType type,
		Pageable pageable
	);
}

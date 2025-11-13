package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.entity.Product;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {
	Optional<Company> findByNameAndAddress_ZipCodeAndType(String name, String zipCode, CompanyType type);

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

	@Query("SELECT c FROM Company c " +
		"LEFT JOIN FETCH c.products p " +
		"WHERE c.id = :companyId " +
		"AND p.id = :productId " +
		"AND p.deletedAt IS NULL")
	Optional<Company> findByIdAndProductIdWithProduct(
		@Param("companyId") UUID companyId,
		@Param("productId") UUID productId
	);

	@Query("SELECT p FROM Company c " +
		"JOIN c.products p " +
		"WHERE (:companyId IS NULL OR c.id = :companyId) " +
		"AND p.deletedAt IS NULL " +
		"AND (:productCategoryIds IS NULL OR p.productCategoryId IN :productCategoryIds)")
	Page<Product> findProductsByCompanyId(
		@Param("companyId") UUID companyId,
		@Param("productCategoryIds") List<UUID> productCategoryIds,
		Pageable pageable
	);
}

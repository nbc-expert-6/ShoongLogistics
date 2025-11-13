package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

@Repository
public interface JpaProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
	Optional<ProductCategory> findByName(String name);

	@Query("SELECT pc FROM ProductCategory pc WHERE " +
		"(:name IS NULL OR pc.name LIKE %:name%)")
	Page<ProductCategory> getProductCategories(
		@Param("name") String name,
		Pageable pageable
	);
}

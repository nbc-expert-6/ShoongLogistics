package com.shoonglogitics.companyservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoonglogitics.companyservice.domain.company.repository.CompanyRepository;
import com.shoonglogitics.companyservice.domain.company.repository.ProductCategoryRepository;
import com.shoonglogitics.companyservice.domain.company.repository.StockRepository;
import com.shoonglogitics.companyservice.infrastructure.repository.CompanyRepositoryAdapter;
import com.shoonglogitics.companyservice.infrastructure.repository.JpaCompanyRepository;
import com.shoonglogitics.companyservice.infrastructure.repository.JpaProductCategoryRepository;
import com.shoonglogitics.companyservice.infrastructure.repository.JpaStockRepository;
import com.shoonglogitics.companyservice.infrastructure.repository.ProductCategoryRepositoryAdapter;
import com.shoonglogitics.companyservice.infrastructure.repository.StockRepositoryAdapter;

@Configuration
public class RepositoryConfig {

	@Bean
	public CompanyRepository companyRepository(JpaCompanyRepository jpaCompanyRepository) {
		return new CompanyRepositoryAdapter(jpaCompanyRepository);
	}

	@Bean
	public ProductCategoryRepository productCategoryRepository(JpaProductCategoryRepository jpaProductCategoryRepository) {
		return new ProductCategoryRepositoryAdapter(jpaProductCategoryRepository);
	}

	@Bean
	public StockRepository stockRepository(JpaStockRepository jpaStockRepository) {
		return new StockRepositoryAdapter(jpaStockRepository);
	}
}


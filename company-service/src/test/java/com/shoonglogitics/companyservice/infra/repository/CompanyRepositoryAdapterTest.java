package com.shoonglogitics.companyservice.infra.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.entity.Product;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyAddress;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;
import com.shoonglogitics.companyservice.infrastructure.repository.CompanyRepositoryAdapter;
import com.shoonglogitics.companyservice.infrastructure.repository.JpaCompanyRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CompanyRepositoryAdapterTest {

	@Autowired
	private CompanyRepositoryAdapter companyRepositoryAdapter;

	@Autowired
	private JpaCompanyRepository jpaCompanyRepository;



	@Test
	@DisplayName("업체를 저장할 수 있다")
	void save() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);

		// When
		Company savedCompany = companyRepositoryAdapter.save(company);

		// Then
		assertThat(savedCompany.getId()).isNotNull();
		assertThat(savedCompany.getName()).isEqualTo("서울 제조 업체");
		assertThat(savedCompany.getType()).isEqualTo(CompanyType.MANUFACTURER);
	}

	@Test
	@DisplayName("업체와 상품을 함께 저장할 수 있다")
	void saveWithProducts() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);

		Product product1 = Product.create(
			UUID.randomUUID(),
			"노트북",
			1500000,
			"고성능 업무용 노트북"
		);

		Product product2 = Product.create(
			UUID.randomUUID(),
			"마우스",
			50000,
			"무선 마우스"
		);

		company.getProducts().add(product1);
		company.getProducts().add(product2);

		// When
		Company savedCompany = companyRepositoryAdapter.save(company);

		// Then
		assertThat(savedCompany.getId()).isNotNull();
		assertThat(savedCompany.getProducts()).hasSize(2);
		assertThat(savedCompany.getProducts())
			.extracting(product -> product.getProductInfo().getName())
			.containsExactly("노트북", "마우스");
		assertThat(savedCompany.getProducts())
			.extracting(product -> product.getProductInfo().getPrice())
			.containsExactly(1500000, 50000);
	}

	@Test
	@DisplayName("업체를 통해 상품 정보를 수정할 수 있다")
	void updateProductThroughCompany() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);

		Product product = Product.create(
			UUID.randomUUID(),
			"노트북",
			1500000,
			"고성능 업무용 노트북"
		);
		company.getProducts().add(product);

		Company savedCompany = companyRepositoryAdapter.save(company);

		// When
		Product foundProduct = savedCompany.getProducts().get(0);
		UUID newCategoryId = UUID.randomUUID();
		foundProduct.changeCategory(newCategoryId);

		Company updatedCompany = companyRepositoryAdapter.save(savedCompany);

		// Then
		assertThat(updatedCompany.getProducts()).hasSize(1);
		assertThat(updatedCompany.getProducts().get(0).getProductCategoryId())
			.isEqualTo(newCategoryId);
	}

	@Test
	@DisplayName("업체를 통해 상품을 제거할 수 있다")
	void removeProductThroughCompany() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);

		Product product1 = Product.create(UUID.randomUUID(), "노트북", 1500000, "고성능 노트북");
		Product product2 = Product.create(UUID.randomUUID(), "마우스", 50000, "무선 마우스");

		company.getProducts().add(product1);
		company.getProducts().add(product2);

		Company savedCompany = companyRepositoryAdapter.save(company);

		// When
		savedCompany.getProducts().remove(0);  // 첫 번째 상품 제거
		Company updatedCompany = companyRepositoryAdapter.save(savedCompany);

		// Then
		assertThat(updatedCompany.getProducts()).hasSize(1);
		assertThat(updatedCompany.getProducts().get(0).getProductInfo().getName())
			.isEqualTo("마우스");
	}

	@Test
	@DisplayName("업체명, 우편번호, 타입으로 업체를 조회할 수 있다")
	void findByNameAndZipCodeAndType() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		jpaCompanyRepository.save(company);

		// When
		Optional<Company> foundCompany = companyRepositoryAdapter.findByNameAndZipCodeAndType(
			"서울 제조 업체",
			"06234",
			CompanyType.MANUFACTURER
		);

		// Then
		assertThat(foundCompany).isPresent();
		assertThat(foundCompany.get().getName()).isEqualTo("서울 제조 업체");
		assertThat(foundCompany.get().getZipCodeValue()).isEqualTo("06234");
		assertThat(foundCompany.get().getType()).isEqualTo(CompanyType.MANUFACTURER);
	}

	@Test
	@DisplayName("업체명이 다르면 조회되지 않는다")
	void findByNameAndZipCodeAndType_differentName() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		jpaCompanyRepository.save(company);

		// When
		Optional<Company> foundCompany = companyRepositoryAdapter.findByNameAndZipCodeAndType(
			"부산 제조 업체",
			"06234",
			CompanyType.MANUFACTURER
		);

		// Then
		assertThat(foundCompany).isEmpty();
	}

	@Test
	@DisplayName("우편번호가 다르면 조회되지 않는다")
	void findByNameAndZipCodeAndType_differentZipCode() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		jpaCompanyRepository.save(company);

		// When
		Optional<Company> foundCompany = companyRepositoryAdapter.findByNameAndZipCodeAndType(
			"서울 제조 업체",
			"48058",
			CompanyType.MANUFACTURER
		);

		// Then
		assertThat(foundCompany).isEmpty();
	}

	@Test
	@DisplayName("타입이 다르면 조회되지 않는다")
	void findByNameAndZipCodeAndType_differentType() {
		// Given
		Company company = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		jpaCompanyRepository.save(company);

		// When
		Optional<Company> foundCompany = companyRepositoryAdapter.findByNameAndZipCodeAndType(
			"서울 제조 업체",
			"06234",
			CompanyType.RECEIVER
		);

		// Then
		assertThat(foundCompany).isEmpty();
	}

	@Test
	@DisplayName("모든 조건으로 업체 목록을 조회할 수 있다")
	void getCompanies_allConditions() {
		// Given
		UUID hubId = UUID.randomUUID();
		Company company1 = createTestCompanyWithHub(hubId, "서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		Company company2 = createTestCompanyWithHub(hubId, "서울 수령 업체", "06234", CompanyType.RECEIVER);
		jpaCompanyRepository.save(company1);
		jpaCompanyRepository.save(company2);

		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(
			hubId,
			"서울",
			CompanyType.MANUFACTURER,
			pageable
		);

		// Then
		assertThat(companies.getContent()).hasSize(1);
		assertThat(companies.getContent().get(0).getName()).isEqualTo("서울 제조 업체");
		assertThat(companies.getContent().get(0).getType()).isEqualTo(CompanyType.MANUFACTURER);
	}

	@Test
	@DisplayName("허브 ID로만 업체 목록을 조회할 수 있다")
	void getCompanies_byHubId() {
		// Given
		UUID hubId1 = UUID.randomUUID();
		UUID hubId2 = UUID.randomUUID();

		Company company1 = createTestCompanyWithHub(hubId1, "서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		Company company2 = createTestCompanyWithHub(hubId1, "부산 제조 업체", "48058", CompanyType.RECEIVER);
		Company company3 = createTestCompanyWithHub(hubId2, "대구 제조 업체", "41940", CompanyType.MANUFACTURER);

		jpaCompanyRepository.save(company1);
		jpaCompanyRepository.save(company2);
		jpaCompanyRepository.save(company3);

		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(hubId1, null, null, pageable);

		// Then
		assertThat(companies.getContent()).hasSize(2);
		assertThat(companies.getContent())
			.extracting(Company::getHubId)
			.containsOnly(hubId1);
	}

	@Test
	@DisplayName("업체명으로 부분 검색할 수 있다")
	void getCompanies_byName() {
		// Given
		Company company1 = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		Company company2 = createTestCompany("서울 수령 업체", "06235", CompanyType.RECEIVER);
		Company company3 = createTestCompany("부산 제조 업체", "48058", CompanyType.MANUFACTURER);

		jpaCompanyRepository.save(company1);
		jpaCompanyRepository.save(company2);
		jpaCompanyRepository.save(company3);

		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(null, "서울", null, pageable);

		// Then
		assertThat(companies.getContent()).hasSize(2);
		assertThat(companies.getContent())
			.extracting(Company::getName)
			.allMatch(name -> name.contains("서울"));
	}

	@Test
	@DisplayName("타입으로 업체 목록을 조회할 수 있다")
	void getCompanies_byType() {
		// Given
		Company company1 = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		Company company2 = createTestCompany("부산 제조 업체", "48058", CompanyType.MANUFACTURER);
		Company company3 = createTestCompany("대구 수령 업체", "41940", CompanyType.RECEIVER);

		jpaCompanyRepository.save(company1);
		jpaCompanyRepository.save(company2);
		jpaCompanyRepository.save(company3);

		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(null, null, CompanyType.MANUFACTURER, pageable);

		// Then
		assertThat(companies.getContent()).hasSize(2);
		assertThat(companies.getContent())
			.extracting(Company::getType)
			.containsOnly(CompanyType.MANUFACTURER);
	}

	@Test
	@DisplayName("조건 없이 모든 업체를 조회할 수 있다")
	void getCompanies_all() {
		// Given
		Company company1 = createTestCompany("서울 제조 업체", "06234", CompanyType.MANUFACTURER);
		Company company2 = createTestCompany("부산 수령 업체", "48058", CompanyType.RECEIVER);
		Company company3 = createTestCompany("대구 제조 업체", "41940", CompanyType.MANUFACTURER);

		jpaCompanyRepository.save(company1);
		jpaCompanyRepository.save(company2);
		jpaCompanyRepository.save(company3);

		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(null, null, null, pageable);

		// Then
		assertThat(companies.getContent()).hasSize(3);
	}

	@Test
	@DisplayName("조회 결과가 없으면 빈 페이지를 반환한다")
	void getCompanies_empty() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Company> companies = companyRepositoryAdapter.getCompanies(
			UUID.randomUUID(),
			"존재하지않는업체",
			null,
			pageable
		);

		// Then
		assertThat(companies.getContent()).isEmpty();
		assertThat(companies.getTotalElements()).isEqualTo(0);
	}

	// ========== 테스트 헬퍼 메서드 ==========

	private Company createTestCompany(String name, String zipCode, CompanyType type) {
		return createTestCompanyWithHub(UUID.randomUUID(), name, zipCode, type);
	}

	private Company createTestCompanyWithHub(UUID hubId, String name, String zipCode, CompanyType type) {
		CompanyAddress address = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층",
			zipCode
		);
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);

		return Company.create(hubId, name, address, location, type);
	}
}

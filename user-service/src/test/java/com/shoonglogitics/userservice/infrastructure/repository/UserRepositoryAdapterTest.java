package com.shoonglogitics.userservice.infrastructure.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;

@SpringBootTest
@Transactional
@Profile(value = "test")
class UserRepositoryAdapterTest {

	@Autowired
	private UserRepositoryAdapter userRepositoryAdapter;

	@Autowired
	private JpaUserRepository jpaUserRepository;

	@Test
	@DisplayName("User 저장 후 ID로 조회")
	void saveAndFindById() {
		// given
		User user = User.builder()
			.userName("testUser")
			.password("password123")
			.userRole(UserRole.MASTER)
			.signupStatus(SignupStatus.PENDING)
			.build();

		// when
		User saved = userRepositoryAdapter.save(user);
		Optional<User> found = userRepositoryAdapter.findById(saved.getId());

		// then
		assertThat(found).isPresent();
		assertThat(found.get().getUserName()).isEqualTo("testUser");
		assertThat(found.get().getUserRole()).isEqualTo(UserRole.MASTER);
		assertThat(found.get().getSignupStatus()).isEqualTo(SignupStatus.PENDING);
	}

	@Test
	@DisplayName("UserName으로 User 조회")
	void findByUserName() {
		// given
		User user = User.builder()
			.userName("user123")
			.password("pass123")
			.userRole(UserRole.HUB_MANAGER)
			.signupStatus(SignupStatus.APPROVED)
			.build();
		userRepositoryAdapter.save(user);

		// when
		Optional<User> found = userRepositoryAdapter.findByUserName("user123");

		// then
		assertThat(found).isPresent();
		assertThat(found.get().getUserName()).isEqualTo("user123");
		assertThat(found.get().getUserRole()).isEqualTo(UserRole.HUB_MANAGER);
		assertThat(found.get().getSignupStatus()).isEqualTo(SignupStatus.APPROVED);
	}

	@Test
	@DisplayName("Master DTO 조회 - 페이징 검증")
	void findMastersPage() {
		// given
		PageRequest pageRequest = PageRequest.of(0, 10);

		// when
		Page<MasterViewResponseDto> page = userRepositoryAdapter.findMasters(pageRequest);

		// then
		assertThat(page).isNotNull();
		page.getContent().forEach(master -> {
			assertThat(master.getMasterId()).isNotNull();
			assertThat(master.getName()).isNotEmpty();
			assertThat(master.getEmail()).isNotEmpty();
		});
	}

	@Test
	@DisplayName("Shipper 조회 - hubId 기준, 저장된 Shipper만 조회")
	void findShippersByHubId_MatchingHubOnly() {
		// given
		UUID hubId = UUID.randomUUID();

		Shipper shipper1 = Shipper.builder()
			.userName("shipper1")
			.password("password")
			.userRole(UserRole.SHIPPER)
			.signupStatus(SignupStatus.APPROVED)
			.hubId(new HubId(hubId))
			.order(5)
			.isShippingAvailable(true)
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.build();

		Shipper shipper2 = Shipper.builder().userName("shipper2")
			.password("password")
			.userRole(UserRole.SHIPPER)
			.signupStatus(SignupStatus.APPROVED)
			.isShippingAvailable(true)
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.hubId(new HubId(hubId)).order(10).build();

		jpaUserRepository.save(shipper1);
		jpaUserRepository.save(shipper2);

		// when
		var page = userRepositoryAdapter.findShippersByHubId(hubId, PageRequest.of(0, 10));

		// then
		assertThat(page).isNotNull();
		assertThat(page.getContent().get(0).getHubId()).isEqualTo(hubId);
	}

	@Test
	@DisplayName("HubManager 조회 - hubId 기준, 삭제된 항목 제외")
	void findHubManagersByHubId_ExcludeDeleted() {
		// given
		UUID hubId = UUID.randomUUID();
		HubManager active = HubManager.builder()
			.userName("activeHub")
			.name(new Name("ActiveHub"))
			.hubId(new HubId(hubId))
			.password("password")
			.signupStatus(SignupStatus.APPROVED)
			.userRole(UserRole.HUB_MANAGER)
			.createdAt(LocalDateTime.now())
			.build();

		HubManager deleted = HubManager.builder()
			.userName("deletedHub")
			.name(new Name("DeletedHub"))
			.hubId(new HubId(hubId))
			.password("password")
			.signupStatus(SignupStatus.APPROVED)
			.userRole(UserRole.HUB_MANAGER)
			.createdAt(LocalDateTime.now())
			.deletedAt(LocalDateTime.now())            // 삭제 처리
			.build();

		jpaUserRepository.save(active);
		jpaUserRepository.save(deleted);

		// when
		List<HubManager> hubManagers = userRepositoryAdapter.findHubManagersByHubId(hubId);

		// then
		assertThat(hubManagers.get(0).getName().getValue()).isEqualTo("ActiveHub");
	}

	@Test
	@DisplayName("Shipper 마지막 주문 조회 - hubId 기준")
	void findLastShipperOrderByHubId_ReturnsMaxOrder() {
		// given
		HubId hubId = new HubId(UUID.randomUUID());
		Shipper shipper1 = Shipper.builder()
			.userName("shipper1")
			.password("password")
			.userRole(UserRole.SHIPPER)
			.signupStatus(SignupStatus.APPROVED)
			.hubId(hubId)
			.order(5)
			.isShippingAvailable(true)
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.build();

		Shipper shipper2 = Shipper.builder().userName("shipper2")
			.password("password")
			.userRole(UserRole.SHIPPER)
			.signupStatus(SignupStatus.APPROVED)
			.isShippingAvailable(true)
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.hubId(hubId).order(10).build();

		jpaUserRepository.save(shipper1);
		jpaUserRepository.save(shipper2);

		// when
		Optional<Integer> lastOrder = userRepositoryAdapter.findLastShipperOrderByHubId(hubId);

		// then
		assertThat(lastOrder).isPresent();
		assertThat(lastOrder.get()).isEqualTo(10);
	}

}
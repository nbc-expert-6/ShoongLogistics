package com.shoonglogitics.userservice.application.service;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.HubManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.MasterSignUpCommand;
import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;
import com.shoonglogitics.userservice.domain.entity.CompanyManager;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.entity.Master;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.repository.UserRepository;
import com.shoonglogitics.userservice.domain.vo.CompanyId;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("Master 저장 테스트")
	void save_master() {
		MasterSignUpCommand command = MasterSignUpCommand.builder()
			.userName("master1")
			.password("12345678")
			.email(new Email("master@gmail.com"))
			.name(new Name("이수현"))
			.slackId(new SlackId("Slack1"))
			.phoneNumber(new PhoneNumber("010-1111-2222"))
			.build();

		userService.signUp(command);

		em.flush();
		em.clear();

		User user = userRepository.findByUsername("master1").orElseThrow();
		assertThat(user.getUserRole()).isEqualTo(UserRole.MASTER);
		assertThat(user).isInstanceOf(Master.class);

		Master master = (Master)user;
		assertThat(master.getEmail().getValue()).isEqualTo("master@gmail.com");
	}

	@Test
	@DisplayName("HubManager 저장 테스트")
	void save_hubManager() {
		HubManagerSignUpCommand command = HubManagerSignUpCommand.builder()
			.userName("hub1")
			.password("12345678")
			.email(new Email("hub@gmail.com"))
			.name(new Name("홍길동"))
			.hubId(new HubId(UUID.randomUUID()))
			.slackId(new SlackId("HubSlack1"))
			.phoneNumber(new PhoneNumber("010-2222-3333"))
			.build();

		userService.signUp(command);

		em.flush();
		em.clear();

		User user = userRepository.findByUsername("hub1").orElseThrow();
		assertThat(user).isInstanceOf(HubManager.class);

		HubManager hubManager = (HubManager)user;
		assertThat(hubManager.getHubId()).isNotNull();
	}

	@Test
	@DisplayName("CompanyManager 저장 테스트")
	void save_companyManager() {
		CompanyManagerSignUpCommand command = CompanyManagerSignUpCommand.builder()
			.userName("company1")
			.password("12345678")
			.email(new Email("company@gmail.com"))
			.name(new Name("김회사"))
			.companyId(new CompanyId(UUID.randomUUID()))
			.slackId(new SlackId("CompanySlack1"))
			.phoneNumber(new PhoneNumber("010-3333-4444"))
			.build();

		userService.signUp(command);

		em.flush();
		em.clear();

		User user = userRepository.findByUsername("company1").orElseThrow();
		assertThat(user).isInstanceOf(CompanyManager.class);

		CompanyManager companyManager = (CompanyManager)user;
		assertThat(companyManager.getCompanyId()).isNotNull();
	}

	@Test
	@DisplayName("Shipper 저장 테스트")
	void save_shipper() {
		ShipperSignUpCommand command = ShipperSignUpCommand.builder()
			.userName("shipper1")
			.password("12345678")
			.email(new Email("shipper@gmail.com"))
			.name(new Name("배달원"))
			.hubId(new HubId(UUID.randomUUID()))
			.slackId(new SlackId("ShipperSlack1"))
			.phoneNumber(new PhoneNumber("010-4444-5555"))
			.order(1)
			.isShippingAvailable(true)
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.build();

		userService.signUp(command);

		em.flush();
		em.clear();

		User user = userRepository.findByUsername("shipper1").orElseThrow();
		assertThat(user).isInstanceOf(Shipper.class);

		Shipper shipper = (Shipper)user;
		assertThat(shipper.getShipperType()).isEqualTo(ShipperType.COMPANY_SHIPPER);
	}

	@Test
	@DisplayName("업체 배송담당자면 hubId 없으면 예외 발생")
	void companyShipperWithoutHubId_shouldFail() {
		ShipperSignUpCommand command = ShipperSignUpCommand.builder()
			.userName("shipper2")
			.password("12345678")
			.email(new Email("shipper2@gmail.com"))
			.name(new Name("홍길동"))
			.slackId(new SlackId("slack1"))
			.phoneNumber(new PhoneNumber("010-1111-2222"))
			.shipperType(ShipperType.COMPANY_SHIPPER)
			.hubId(null) // hubId 누락
			.order(1)
			.isShippingAvailable(true)
			.build();

		assertThatThrownBy(() -> userService.signUp(command))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("업체 배송 담당자는 허브 ID가 반드시 필요합니다.");
	}

	@Test
	@DisplayName("허브 배송담당자면 hubId 있으면 예외 발생")
	void hubShipperWithHubId_shouldFail() {
		ShipperSignUpCommand command = ShipperSignUpCommand.builder()
			.userName("shipper3")
			.password("12345678")
			.email(new Email("shipper3@gmail.com"))
			.name(new Name("김철수"))
			.slackId(new SlackId("slack2"))
			.phoneNumber(new PhoneNumber("010-2222-3333"))
			.shipperType(ShipperType.HUB_SHIPPER)
			.hubId(new HubId(UUID.randomUUID())) // hubId 존재 → 예외
			.order(1)
			.isShippingAvailable(true)
			.build();

		assertThatThrownBy(() -> userService.signUp(command))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("허브 배송 담당자는 허브 ID가 없어야 합니다.");
	}
}

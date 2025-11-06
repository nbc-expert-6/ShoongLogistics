package com.shoonglogitics.userservice.application.service;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.UserServiceApplication;
import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.HubManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.MasterSignUpCommand;
import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;
import com.shoonglogitics.userservice.application.command.UpdateUserCommand;
import com.shoonglogitics.userservice.domain.entity.CompanyManager;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.entity.Master;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.entity.ShipperType;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
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

@SpringBootTest(classes = UserServiceApplication.class)
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
			.isShippingAvailable(true)
			.build();

		assertThatThrownBy(() -> userService.signUp(command))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("허브 배송 담당자는 허브 ID가 없어야 합니다.");
	}

	@Test
	void approveSignup_pendingUser_dbPersists() {
		// given
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

		userService.updateSignupStatus(user.getId(), "APPROVED");

		em.flush();
		em.clear();

		User updatedUser = userRepository.findByUsername("master1").orElseThrow();
		assertThat(updatedUser.getSignupStatus()).isEqualTo(SignupStatus.APPROVED);
	}

	//====================================================================
	@Test
	@DisplayName("updateSignupStatus - 지원하지 않는 상태값이면 예외 발생")
	void updateSignupStatus_invalidStatus_throwsException() {
		MasterSignUpCommand command = MasterSignUpCommand.builder()
			.userName("master2")
			.password("12345678")
			.email(new Email("master2@gmail.com"))
			.name(new Name("테스트"))
			.slackId(new SlackId("Slack2"))
			.phoneNumber(new PhoneNumber("010-5555-6666"))
			.build();

		userService.signUp(command);
		em.flush();
		em.clear();

		User user = userRepository.findByUsername("master2").orElseThrow();

		assertThatThrownBy(() -> userService.updateSignupStatus(user.getId(), "UNKNOWN"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("지원하지 않는 상태값입니다");
	}

	@Test
	@DisplayName("updateUser - Master 타입이면 정보 수정")
	void updateUser_success() {
		// given: Master 생성
		MasterSignUpCommand command = MasterSignUpCommand.builder()
			.userName("master3")
			.password("12345678")
			.email(new Email("master3@gmail.com"))
			.name(new Name("업데이트 전"))
			.slackId(new SlackId("Slack3"))
			.phoneNumber(new PhoneNumber("010-7777-8888"))
			.build();

		userService.signUp(command);
		em.flush();
		em.clear();

		Master master = (Master)userRepository.findByUsername("master3").orElseThrow();

		// when: 정보 수정
		UpdateUserCommand updateCommand = new UpdateUserCommand(
			"업데이트 후",
			"updated3@gmail.com",
			"UpdatedSlack",
			"010-9999-0000"
		);

		userService.updateUser(master.getId(), updateCommand);
		em.flush();
		em.clear();

		// then: 값 검증
		Master updatedMaster = (Master)userRepository.findByUsername("master3").orElseThrow();
		assertThat(updatedMaster.getName().getValue()).isEqualTo("업데이트 후");
		assertThat(updatedMaster.getEmail().getValue()).isEqualTo("updated3@gmail.com");
		assertThat(updatedMaster.getSlackId().getValue()).isEqualTo("UpdatedSlack");
		assertThat(updatedMaster.getPhoneNumber().getValue()).isEqualTo("010-9999-0000");
	}

	@Test
	@DisplayName("deleteUser - 정상 삭제")
	void deleteUser_success() {
		MasterSignUpCommand command = MasterSignUpCommand.builder()
			.userName("master4")
			.password("12345678")
			.email(new Email("master4@gmail.com"))
			.name(new Name("삭제 전"))
			.slackId(new SlackId("Slack4"))
			.phoneNumber(new PhoneNumber("010-1234-5678"))
			.build();

		userService.signUp(command);
		em.flush();
		em.clear();

		User user = userRepository.findByUsername("master4").orElseThrow();

		userService.deleteUser(user.getId());
		em.flush();
		em.clear();

		User deletedUser = userRepository.findByUsername("master4").orElseThrow();
		assertThat(deletedUser.isDeleted()).isTrue();
	}

	@Test
	@DisplayName("deleteUser - 이미 삭제된 사용자면 예외 발생")
	void deleteUser_alreadyDeleted_throwsException() {
		MasterSignUpCommand command = MasterSignUpCommand.builder()
			.userName("master5")
			.password("12345678")
			.email(new Email("master5@gmail.com"))
			.name(new Name("삭제 예외"))
			.slackId(new SlackId("Slack5"))
			.phoneNumber(new PhoneNumber("010-2345-6789"))
			.build();

		userService.signUp(command);
		em.flush();
		em.clear();

		User user = userRepository.findByUsername("master5").orElseThrow();
		user.softDelete(user.getId());
		em.flush();
		em.clear();

		assertThatThrownBy(() -> userService.deleteUser(user.getId()))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("이미 삭제된 사용자입니다.");
	}

	@Test
	@DisplayName("canUpdateUser / canDeleteUser 권한 체크 - MASTER 항상 true")
	void canUpdateAndDelete_master() {
		assertThat(userService.canUpdateUser("MASTER", 1L, 2L)).isTrue();
		assertThat(userService.canDeleteUser("MASTER", 1L, 2L)).isTrue();
	}

	@Test
	@DisplayName("canUpdateUser / canDeleteUser 권한 체크 - HUB_MANAGER 제한 확인")
	void canUpdateAndDelete_hubManager() {
		// HubManager 생성 및 hubId 동일/다른 경우 테스트
		UUID hubId1 = UUID.randomUUID();
		HubManager manager = HubManager.builder()
			.userName("hubManager")
			.password("12345678")
			.hubId(new HubId(hubId1))
			.signupStatus(SignupStatus.PENDING)
			.userRole(UserRole.HUB_MANAGER)
			.build();
		userRepository.save(manager);

		HubManager targetManagerSameHub = HubManager.builder()
			.userName("hubTarget1")
			.password("12345678")
			.hubId(new HubId(hubId1))
			.signupStatus(SignupStatus.PENDING)
			.userRole(UserRole.HUB_MANAGER)
			.build();
		userRepository.save(targetManagerSameHub);

		HubManager targetManagerDiffHub = HubManager.builder()
			.userName("hubTarget2")
			.password("12345678")
			.hubId(new HubId(UUID.randomUUID()))
			.signupStatus(SignupStatus.PENDING)
			.userRole(UserRole.HUB_MANAGER)
			.build();
		userRepository.save(targetManagerDiffHub);

		em.flush();
		em.clear();

		// 같은 허브면 true
		assertThat(userService.canUpdateUser("HUB_MANAGER", manager.getId(), targetManagerSameHub.getId())).isTrue();
		assertThat(userService.canDeleteUser("HUB_MANAGER", manager.getId(), targetManagerSameHub.getId())).isTrue();

		// 다른 허브면 false
		assertThat(userService.canUpdateUser("HUB_MANAGER", manager.getId(), targetManagerDiffHub.getId())).isFalse();
		assertThat(userService.canDeleteUser("HUB_MANAGER", manager.getId(), targetManagerDiffHub.getId())).isFalse();
	}

}

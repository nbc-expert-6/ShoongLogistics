package com.shoonglogitics.userservice.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

class DomainTest {

	@Test
	@DisplayName("정상적인 회원 생성 테스트")
	void createUser_success() {
		String userName = "brian123";
		String password = "Brian981103!";

		User user = User.create(userName, password, UserRole.MASTER);

		assertThat(user.getUserName()).isEqualTo(userName);
		assertThat(user.getPassword()).isEqualTo(password);
		assertThat(user.getUserRole()).isEqualTo(UserRole.MASTER);
		assertThat(user.getSignupStatus()).isEqualTo(SignupStatus.PENDING);
	}

	@Test
	@DisplayName("아이디가 null이면 예외발생")
	void createUser_nullUserName_throwsException() {
		// given
		String userName = null;
		String password = "Brian981103!";

		// when & then
		assertThatThrownBy(() -> User.create(userName, password, UserRole.MASTER))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("아이디 값은 필수입니다.");
	}

	@Test
	@DisplayName("아이디 길이가 4자 미만이면 예외 발생")
	void createUser_shortUserName_throwsException() {
		// given
		String userName = "abc";
		String password = "Brian981103!";

		// when & then
		assertThatThrownBy(() -> User.create(userName, password, UserRole.MASTER))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("아이디는 4자 이상 10자 이하이어야 합니다.");
	}

	@Test
	@DisplayName("비밀번호가 null이면 예외 발생")
	void createUser_nullPassword_throwsException() {
		// given
		String userName = "brian123";
		String password = null;

		// when & then
		assertThatThrownBy(() -> User.create(userName, password, UserRole.MASTER))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호 값은 필수입니다.");
	}

	@Test
	@DisplayName("비밀번호 길이가 8자 미만이면 예외 발생")
	void createUser_shortPassword_throwsException() {
		// given
		String userName = "brian123";
		String password = "short";

		// when & then
		assertThatThrownBy(() -> User.create(userName, password, UserRole.MASTER))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호는 8자 이상 15자 이하이어야 합니다.");
	}

	@Test
	@DisplayName("회원 승인 테스트")
	void approveSignup_success() {
		// given
		User user = User.create("brian123", "Brian981103!", UserRole.MASTER);

		// when
		user.approveSignup();

		// then
		assertThat(user.getSignupStatus()).isEqualTo(SignupStatus.APPROVED);
	}

	@Test
	@DisplayName("승인 상태가 아닌 경우 승인 시 예외 발생")
	void approveSignup_invalidState_throwsException() {
		// given
		User user = User.create("brian123", "Brian981103!", UserRole.MASTER);
		user.approveSignup(); // 이미 APPROVED 상태

		// when & then
		assertThatThrownBy(user::approveSignup)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("승인할 수 없는 상태입니다.");
	}

	@Test
	@DisplayName("필수 값이 null이면 예외 발생 테스트")
	void createMaster_nullVO_throwsException() {
		String userName = "master1";
		String password = "password1";
		Email email = null;
		Name name = null;
		SlackId slackId = null;
		PhoneNumber phoneNumber = null;

		// when & then
		assertThatThrownBy(() -> Master.create(userName, password, email, new Name("Name"),
			new SlackId("slack"), new PhoneNumber("01012345678")))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이메일은 필수 값입니다.");

		assertThatThrownBy(() -> Master.create(userName, password, new Email("email@example.com"),
			null, new SlackId("slack"), new PhoneNumber("01012345678")))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이름은 필수 값입니다.");

		assertThatThrownBy(() -> Master.create(userName, password, new Email("email@example.com"),
			new Name("Name"), null, new PhoneNumber("01012345678")))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Slack ID는 필수 값입니다.");

		assertThatThrownBy(() -> Master.create(userName, password, new Email("email@example.com"),
			new Name("Name"), new SlackId("slack"), null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("전화번호는 필수 값입니다.");
	}

	@Test
	@DisplayName("사용자 정보 수정 테스트")
	void updateUserInfo_success() {
		// given
		Master master = Master.create(
			"master1",
			"Brian981103!",
			new Email("master@example.com"),
			new Name("Master Name"),
			new SlackId("master_slack"),
			new PhoneNumber("01012345678")
		);

		Name newName = new Name("Updated Name");
		Email newEmail = new Email("updated@example.com");
		SlackId newSlackId = new SlackId("updated_slack");
		PhoneNumber newPhoneNumber = new PhoneNumber("01087654321");

		// when
		master.updateUserInfo(newName, newEmail, newSlackId, newPhoneNumber);

		// then
		assertThat(master.getName()).isEqualTo(newName);
		assertThat(master.getEmail()).isEqualTo(newEmail);
		assertThat(master.getSlackId()).isEqualTo(newSlackId);
		assertThat(master.getPhoneNumber()).isEqualTo(newPhoneNumber);
	}

	@Test
	@DisplayName("정상적인 Master 생성 테스트")
	void createMaster_success() {
		// given
		String userName = "master1";
		String password = "Brian981103!";
		Email email = new Email("master@example.com");
		Name name = new Name("Master Name");
		SlackId slackId = new SlackId("master_slack");
		PhoneNumber phoneNumber = new PhoneNumber("01012345678");

		// when
		Master master = Master.create(userName, password, email, name, slackId, phoneNumber);

		// then
		assertThat(master.getUserName()).isEqualTo(userName);
		assertThat(master.getPassword()).isEqualTo(password);
		assertThat(master.getUserRole()).isEqualTo(UserRole.MASTER);
		assertThat(master.getSignupStatus()).isEqualTo(SignupStatus.PENDING);
		assertThat(master.getEmail()).isEqualTo(email);
		assertThat(master.getName()).isEqualTo(name);
		assertThat(master.getSlackId()).isEqualTo(slackId);
		assertThat(master.getPhoneNumber()).isEqualTo(phoneNumber);
	}

}
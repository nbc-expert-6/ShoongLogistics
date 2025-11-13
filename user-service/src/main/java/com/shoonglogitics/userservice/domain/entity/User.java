package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "p_user")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false, unique = true, length = 10)
	private String userName;

	@Column(name = "password", nullable = false, length = 15)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	@Enumerated(EnumType.STRING)
	@Column(name = "signup_status", nullable = false)
	private SignupStatus signupStatus;

	protected User(String userName, String password, UserRole userRole, SignupStatus signupStatus) {
		this.userName = userName;
		this.password = password;
		this.userRole = userRole;
		this.signupStatus = signupStatus;
	}

	public static User create(String userName, String password, UserRole userRole) {
		validateUserName(userName);
		validatePassword(password);

		return new User(userName, password, userRole, SignupStatus.PENDING);
	}

	private static void validateUserName(String userName) {
		if (userName == null || userName.isEmpty()) {
			throw new IllegalArgumentException("아이디 값은 필수입니다.");
		}
		if (userName.length() < 4 || userName.length() > 10) {
			throw new IllegalArgumentException("아이디는 4자 이상 10자 이하이어야 합니다.");
		}
	}

	private static void validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("비밀번호 값은 필수입니다.");
		}
		if (password.length() < 8 || password.length() > 15) {
			throw new IllegalArgumentException("비밀번호는 8자 이상 15자 이하이어야 합니다.");
		}
	}

	public void approveSignup() {
		if (this.signupStatus != SignupStatus.PENDING) {
			throw new IllegalArgumentException("승인할 수 없는 상태입니다.");
		}
		this.signupStatus = SignupStatus.APPROVED;

	}

	public void rejectSignup() {
		if (this.signupStatus != SignupStatus.PENDING) {
			throw new IllegalArgumentException("승인할 수 없는 상태입니다.");
		}
		this.signupStatus = SignupStatus.REJECTED;

	}

}

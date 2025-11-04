package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.vo.CompanyId;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "p_company_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyManager extends User {

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "company_id"))
	private CompanyId companyId;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "email"))
	private Email email;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "name"))
	private Name name;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "slack_id"))
	private SlackId slackId;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "phone_number"))
	private PhoneNumber phoneNumber;

	public static CompanyManager create(String userName, String password, CompanyId companyId,
		Email email, Name name, SlackId slackId, PhoneNumber phoneNumber) {
		validateVO(email, name, slackId, phoneNumber);
		return CompanyManager.builder()
			.userName(userName)
			.password(password)
			.signupStatus(SignupStatus.PENDING)
			.userRole(UserRole.COMPANY_MANAGER)
			.companyId(companyId)
			.email(email)
			.name(name)
			.slackId(slackId)
			.phoneNumber(phoneNumber)
			.build();
	}

	private static void validateVO(Email email, Name name, SlackId slackId, PhoneNumber phoneNumber) {
		if (email == null)
			throw new IllegalArgumentException("이메일은 필수 값입니다.");
		if (name == null)
			throw new IllegalArgumentException("이름은 필수 값입니다.");
		if (slackId == null)
			throw new IllegalArgumentException("Slack ID는 필수 값입니다.");
		if (phoneNumber == null)
			throw new IllegalArgumentException("전화번호는 필수 값입니다.");
	}

}

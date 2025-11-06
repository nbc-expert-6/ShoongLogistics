package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
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
@Table(name = "p_hub_manager")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubManager extends User implements UserUpdatable {

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "hub_id"))
	private HubId hubId;

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

	public HubManager(String userName, String password,
		Email email, Name name, SlackId slackId,
		PhoneNumber phoneNumber, HubId hubId) {
		super(userName, password, UserRole.HUB_MANAGER, SignupStatus.PENDING);
		this.email = email;
		this.name = name;
		this.slackId = slackId;
		this.phoneNumber = phoneNumber;
		this.hubId = hubId;
	}

	// 정적 팩토리 메서드
	public static HubManager create(String userName, String password,
		Email email, Name name, SlackId slackId,
		PhoneNumber phoneNumber, HubId hubId) {
		validateVO(email, name, slackId, phoneNumber);
		return HubManager.builder()
			.userName(userName)
			.password(password)
			.signupStatus(SignupStatus.PENDING)
			.userRole(UserRole.HUB_MANAGER)
			.hubId(hubId)
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

	@Override
	public void updateUserInfo(Name name, Email email, SlackId slackId, PhoneNumber phoneNumber) {
		this.name = name;
		this.email = email;
		this.slackId = slackId;
		this.phoneNumber = phoneNumber;
	}
}

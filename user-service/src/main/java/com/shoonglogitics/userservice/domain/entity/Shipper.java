package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@DiscriminatorValue("SHIPPER")
@Table(name = "p_shipper")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipper extends User implements UserUpdatable {

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "hub_id", nullable = true))
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

	@Enumerated(EnumType.STRING)
	@Column(name = "shipper_type", nullable = false)
	private ShipperType shipperType;

	@Column(name = "shipping_order", nullable = false)
	private Integer order;

	@Column(name = "is_shipping_available", nullable = false)
	private Boolean isShippingAvailable;

	public static Shipper create(String userName, String password, HubId hubId, Email email, Name name,
		SlackId slackId, PhoneNumber phoneNumber, ShipperType shipperType,
		Integer order, Boolean isShippingAvailable) {

		// shipperType 검증
		if (shipperType == ShipperType.COMPANY_SHIPPER && hubId == null) {
			throw new IllegalArgumentException("업체 배송 담당자는 허브 ID가 반드시 필요합니다.");
		}
		if (shipperType == ShipperType.HUB_SHIPPER && hubId != null) {
			throw new IllegalArgumentException("허브 배송 담당자는 허브 ID가 없어야 합니다.");
		}

		return Shipper.builder()
			.userName(userName)
			.password(password)
			.userRole(UserRole.SHIPPER)
			.signupStatus(SignupStatus.PENDING)
			.hubId(hubId)
			.email(email)
			.name(name)
			.slackId(slackId)
			.phoneNumber(phoneNumber)
			.shipperType(shipperType)
			.order(order)
			.isShippingAvailable(isShippingAvailable)
			.build();
	}

	@Override
	public void updateUserInfo(Name name, Email email, SlackId slackId, PhoneNumber phoneNumber) {
		this.name = name;
		this.email = email;
		this.slackId = slackId;
		this.phoneNumber = phoneNumber;
	}

}

package com.shoonglogitics.userservice.domain.entity;

import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.HubId;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_hub_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "hub_id"))
	private HubId hubId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "p_user_id", nullable = false)
	private User user;

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

	@Builder
	public HubManager(HubId hubId, User user, Email email, Name name,
		SlackId slackId, PhoneNumber phoneNumber) {
		this.hubId = hubId;
		this.user = user;
		this.email = email;
		this.name = name;
		this.slackId = slackId;
		this.phoneNumber = phoneNumber;
	}

}

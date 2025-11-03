package com.shoonglogitics.userservice.domain.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractAggregateRoot<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false, unique = true, length = 10)
	private String userName;

	@Column(name = "password", nullable = false, length = 15)
	private String password;

	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(name = "signup_status", nullable = false)
	private SignupStatus signupStatus;

	@Builder
	public User(String userName, String password, UserRole userRole, SignupStatus signupStatus) {
		this.userName = userName;
		this.password = password;
		this.userRole = userRole;
		this.signupStatus = signupStatus;
	}

}

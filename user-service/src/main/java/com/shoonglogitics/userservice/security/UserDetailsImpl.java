package com.shoonglogitics.userservice.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.entity.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private final User user;

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserRole role = user.getUserRole();
		String authority = role.getAuthority();

		SimpleGrantedAuthority simpleGrantedAuthority =
			new SimpleGrantedAuthority(authority);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getSignupStatus() == SignupStatus.APPROVED;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getSignupStatus() == SignupStatus.APPROVED;
	}

}

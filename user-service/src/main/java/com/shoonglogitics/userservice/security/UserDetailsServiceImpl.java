package com.shoonglogitics.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(
				username + "의 아이디로 회원을 찾을 수 없습니다.// TODO : AndDeletedAtIsNull도 추가하기"));

		return new UserDetailsImpl(user);
	}

}

package com.shoonglogitics.userservice.domain.repository;

import java.util.Optional;

import com.shoonglogitics.userservice.domain.entity.User;

public interface UserRepository {

	// TODO : AndDeletedAtIsNull도 추가하기
	Optional<User> findByUsername(String username);

}

package com.shoonglogitics.userservice.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.userservice.domain.entity.User;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

	// TODO : AndDeletedAtIsNull도 추가하기
	Optional<User> findByUserName(String userName);

}

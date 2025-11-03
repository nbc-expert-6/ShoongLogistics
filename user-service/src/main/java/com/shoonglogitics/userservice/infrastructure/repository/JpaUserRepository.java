package com.shoonglogitics.userservice.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.userservice.domain.entity.User;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
}

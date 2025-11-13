package com.shoonglogitics.userservice.application.strategy.view;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserViewStrategy<T> {

	Page<T> findUsers(Pageable pageable, UUID hubId);

	Optional<T> findUserById(Long id);

}

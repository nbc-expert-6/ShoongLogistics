package com.shoonglogitics.hubservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.hubservice.domain.entity.Hub;

public interface HubRepository {

	Hub save(Hub hub);

	Optional<Hub> findById(UUID id);

	List<Hub> findAll();

}

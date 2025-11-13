package com.shoonglogitics.hubservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.vo.HubType;

public interface HubRepository {

	Hub save(Hub hub);

	Optional<Hub> findById(UUID id);

	List<Hub> findAll();

	List<Hub> findByHubType(HubType hubType);

	Double calculateDistanceInMeters(UUID fromId, UUID toId);
}

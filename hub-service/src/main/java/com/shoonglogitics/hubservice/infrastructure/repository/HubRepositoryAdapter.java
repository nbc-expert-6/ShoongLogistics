package com.shoonglogitics.hubservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.hubservice.domain.vo.HubType;
import org.springframework.stereotype.Component;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.repository.HubRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HubRepositoryAdapter implements HubRepository {

	private final JpaHubRepository jpaHubRepository;

	@Override
	public Hub save(Hub hub) {
		return jpaHubRepository.save(hub);
	}

	@Override
	public Optional<Hub> findById(UUID id) {
		return jpaHubRepository.findById(id)
				.filter(hub -> !hub.isDeleted());
	}


	@Override
	public List<Hub> findAll() {
		return jpaHubRepository.findAll().stream().filter(hub -> !hub.isDeleted())
				.toList();
	}


	@Override
	public List<Hub> findByHubType(HubType hubType) {
		return jpaHubRepository.findByHubType(hubType).stream()
				.filter(hub -> !hub.isDeleted())
				.toList();
	}

	@Override
	public Double calculateDistanceInMeters(UUID fromId, UUID toId) {
		return jpaHubRepository.calculateDistanceInMeters(fromId, toId);
	}


}

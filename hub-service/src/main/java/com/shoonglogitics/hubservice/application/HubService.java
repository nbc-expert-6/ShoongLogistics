package com.shoonglogitics.hubservice.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.hubservice.application.command.CreateHubCommand;
import com.shoonglogitics.hubservice.application.dto.HubResult;
import com.shoonglogitics.hubservice.application.dto.HubSummary;
import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.repository.HubRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class HubService {

	private final HubRepository hubRepository;

	@Transactional
	public Hub createHub(CreateHubCommand command) {
		Hub hub = Hub.create(
			command.name(),
			command.address(),
			command.latitude(),
			command.longitude(),
			command.hubType()
		);
		return hubRepository.save(hub);
	}

	@Transactional(readOnly = true)
	@Cacheable(value ="hub", key = "#hubId")
	public HubResult getHub(UUID hubId) {

		Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new IllegalArgumentException("Hub를 찾을 수 없습니다."));
		return HubResult.from(hub);

	}

	@Transactional(readOnly = true)
	@Cacheable(value = "hubs")
	public List<HubSummary> getAllHubs() {
		return hubRepository.findAll().stream()
			.map(HubSummary::from)
			.collect(Collectors.toList());
	}


	@Transactional
	public void deleteHub(UUID hubId, Long userId) {
		Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브 입니다."));

		hub.deactivate(userId);

		hubRepository.save(hub);

	}
}

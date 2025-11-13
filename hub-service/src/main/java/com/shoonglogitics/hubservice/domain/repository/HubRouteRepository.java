package com.shoonglogitics.hubservice.domain.repository;

import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.vo.HubId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository {

    HubRoute save(HubRoute hubRoute);

    Optional<HubRoute> findById(UUID id);

    Optional<HubRoute> findByDepartureAndArrival(HubId departure, HubId arrival);

    List<HubRoute> findAllActive();

    List<HubRoute> findByDepartureHubId(HubId departureId);

    long count();
}

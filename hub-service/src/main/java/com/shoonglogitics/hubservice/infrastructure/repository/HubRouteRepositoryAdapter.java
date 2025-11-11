package com.shoonglogitics.hubservice.infrastructure.repository;

import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.repository.HubRouteRepository;
import com.shoonglogitics.hubservice.domain.vo.HubId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HubRouteRepositoryAdapter implements HubRouteRepository {

    private final JpaHubRouteRepository jpaHubRouteRepository;

    @Override
    public HubRoute save(HubRoute hubRoute){
        return jpaHubRouteRepository.save(hubRoute);
    }

    @Override
    public Optional<HubRoute> findById(UUID hubRouteId){
        return jpaHubRouteRepository.findById(hubRouteId)
                .filter(hubRoute -> !hubRoute.isDeleted());
    }

    @Override
    public Optional<HubRoute> findByDepartureAndArrival(HubId departure, HubId arrival) {
        return jpaHubRouteRepository.findByDepartureAndArrival(departure, arrival);
    }

    @Override
    public List<HubRoute> findAllActive() {
        return jpaHubRouteRepository.findAllActive();
    }

    @Override
    public List<HubRoute> findByDepartureHubId(HubId departureId) {
        return jpaHubRouteRepository.findByDepartureHubId(departureId);
    }

    @Override
    public long count() {
        return jpaHubRouteRepository.count();
    }
}

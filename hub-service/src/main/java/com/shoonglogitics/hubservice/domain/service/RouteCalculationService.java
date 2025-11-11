package com.shoonglogitics.hubservice.domain.service;


import com.shoonglogitics.hubservice.application.dto.RouteResult;
import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.repository.HubRepository;
import com.shoonglogitics.hubservice.domain.vo.Distance;
import com.shoonglogitics.hubservice.domain.vo.HubType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteCalculationService {

    private final HubRepository hubRepository;
    private final DistanceCalculator distanceCalculator;

    private static final int DIRECT_ROUTE_THRESHOLD_METERS = 200_000;


    public RouteResult calculateOptimalRoute(UUID departureId, UUID arrivalId){
        Hub fromHub = hubRepository.findById(departureId)
                .orElseThrow(() -> new IllegalArgumentException("출발 허브를 찾을 수 없습니다: " + departureId));
        Hub toHub = hubRepository.findById(arrivalId)
                .orElseThrow(() -> new IllegalArgumentException("도착 허브를 찾을 수 없습니다: "+ arrivalId));


        Distance distance = distanceCalculator.calculateDistance(fromHub, toHub);

        if(distance.getMeters() < DIRECT_ROUTE_THRESHOLD_METERS){
            log.info("직접 배송 경로: {} -> {} ({}km)",
            fromHub.getName(), toHub.getName(), distance.getMeters()/1000.0);

            return RouteResult.createDirect(
                    fromHub.getId(),
                    toHub.getId(),
                    distance.getMeters()
            );
        }

        Hub relayHub = findOptimalRelayHub(fromHub, toHub);

        Distance firstLegDistance = distanceCalculator.calculateDistance(fromHub, relayHub);
        Distance secondLegDistance = distanceCalculator.calculateDistance(relayHub, toHub);

        log.info("중계 배송 경로: {} -> {} -> {} (총 {}km)",
                fromHub.getName(), relayHub.getName(), toHub.getName(),
                (firstLegDistance.getMeters() + secondLegDistance.getMeters()) / 1000.0);

        return RouteResult.createRelay(
                fromHub.getId(),
                relayHub.getId(),
                toHub.getId(),
                firstLegDistance.getMeters(),
                secondLegDistance.getMeters()
        );
    }

    private Hub findOptimalRelayHub(Hub from, Hub to){
        List<Hub> centralHubs = hubRepository.findByHubType(HubType.CENTRAL);

        if(centralHubs.isEmpty()){
            throw new IllegalStateException("중앙 허브가 존재하지 않습니다.");
        }

        return centralHubs.stream()
                .min(Comparator.comparingDouble(relay -> {
                    Distance d1 = distanceCalculator.calculateDistance(from, relay);
                    Distance d2 = distanceCalculator.calculateDistance(relay, to);
                    return d1.getMeters() + d2.getMeters();
                }))
                .orElseThrow(() -> new IllegalArgumentException("최적 경우지를 찾을 수 없습니다."));
    }
}

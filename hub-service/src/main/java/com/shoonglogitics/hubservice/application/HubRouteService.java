package com.shoonglogitics.hubservice.application;

import com.shoonglogitics.hubservice.application.dto.RouteResult;
import com.shoonglogitics.hubservice.domain.service.RouteCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class HubRouteService {

    private final RouteCalculationService routeCalculationService;

    @Cacheable(value = "routes", key = "#departureId + ':' + #arrivalId")
    public RouteResult calculateRoute(UUID departureId, UUID arrivalId){
        log.info("경로 계산 요청 : departureId={}, arrivalId={}", departureId, arrivalId);
        return routeCalculationService.calculateOptimalRoute(departureId, arrivalId);
    }
}

package com.shoonglogitics.hubservice.infrastructure.initializer;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.repository.HubRepository;
import com.shoonglogitics.hubservice.domain.repository.HubRouteRepository;
import com.shoonglogitics.hubservice.domain.service.DistanceCalculator;
import com.shoonglogitics.hubservice.domain.vo.Distance;
import com.shoonglogitics.hubservice.domain.vo.Duration;
import com.shoonglogitics.hubservice.domain.vo.HubId;
import com.shoonglogitics.hubservice.domain.vo.RouteType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubRouteDataInitializer implements CommandLineRunner {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;
    private final DistanceCalculator distanceCalculator;

    private static final int DIRECT_ROUTE_THRESHOLD_METERS = 200_000;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(hubRouteRepository.count() > 0){
            log.info("HubRoute 데이터가 이미 존재합니다. 초기화 SKIP");
            return;
        }

        log.info("=== Hub 경로 데이터 생성 시작 == ");

        List<Hub> hubs = hubRepository.findAll();

        if(hubs.isEmpty()){
            log.warn("허브 데이터가 없습니다. HubRoute를 생성할 수 없습니다.");
            return;
        }

        int directCount =0 ;
        int relayCount = 0;

        for(Hub from : hubs){
            for(Hub to : hubs){
                if(from.getId().equals(to.getId())){
                    continue;
                }

                try {
                    Distance distance = distanceCalculator.calculateDistance(from, to);

                    //direct/relay 결정
                    RouteType routeType = distance.getMeters() < DIRECT_ROUTE_THRESHOLD_METERS
                            ? RouteType.DIRECT
                            : RouteType.RELAY;

                    HubRoute route = HubRoute.create(
                            HubId.of(from.getId()),
                            HubId.of(to.getId()),
                    distance,
                    routeType
                    );

                    hubRouteRepository.save(route);

                    if (routeType == RouteType.DIRECT) {
                        directCount++;
                    } else {
                        relayCount++;
                    }

                    log.debug("경로 생성: {} -> {} = {:.2f}km ({})",
                            from.getName(), to.getName(),
                            distance.getMeters() / 1000.0, routeType);

                } catch (Exception e) {
                    log.error("경로 생성 실패: {} -> {}, 에러: {}",
                            from.getName(), to.getName(), e.getMessage());
                }
            }
        }
        log.info("=== HubRoute 데이터 생성 완료 ===");
        log.info("총 {}개 경로 생성 (직접 배송: {}, 중계 배송: {})",
                directCount + relayCount, directCount, relayCount);

    }
}

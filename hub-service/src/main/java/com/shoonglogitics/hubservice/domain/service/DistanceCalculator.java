package com.shoonglogitics.hubservice.domain.service;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import com.shoonglogitics.hubservice.domain.repository.HubRepository;
import com.shoonglogitics.hubservice.domain.vo.Distance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistanceCalculator {


    private final HubRepository hubRepository;

    public Distance calculateDistance(Hub from, Hub to){
        Double meters = hubRepository.calculateDistanceInMeters(from.getId(), to.getId());

        if(meters == null){
            throw new IllegalArgumentException(
                    String.format("거리 계산 실패: fromId=%s, toId=%s", from.getId(), to.getId())
            );
        }

        return Distance.ofMeters((int) Math.round(meters));

    }

}
